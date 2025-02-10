package itu.crypto.firebase.firestore.generalisation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@AllArgsConstructor
public abstract class FirestoreChangeListener<T, D> {

    private final Firestore firestore;
    private final BaseService<T> baseService;
    @Setter
    @Getter
    private String collectionName;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    protected abstract T toEntity(D document);

    protected abstract Class<D> getDocumentClass();

    protected abstract void updateDatabase(T entity);

    protected abstract void deleteFromDatabase(String entityId);


    // Ensemble des documents en cours de traitement.
    private final Set<String> processingDocuments = ConcurrentHashMap.newKeySet();
    // Ensemble des documents traités et supprimés définitivement.
    private final Set<String> processedDocuments = ConcurrentHashMap.newKeySet();

    /**
     * Démarre l’écoute des changements sur la collection associée.
     */
    public void startListening() {
        CollectionReference collectionRef = firestore.collection(collectionName);
        collectionRef.addSnapshotListener(executorService, (snapshots, error) -> {
            if (error != null) {
                log.error("Erreur lors de l'écoute de Firestore : {}", error.getMessage());
                return;
            }

            assert snapshots != null;
            for (DocumentChange change : snapshots.getDocumentChanges()) {
                DocumentSnapshot docSnapshot = change.getDocument();
                String entityId = docSnapshot.getId();

                // Si le document a déjà été traité, on l'ignore
                if (processedDocuments.contains(entityId)) {
                    log.info("Document {} déjà traité et supprimé.", entityId);
                    continue;
                }
                // Si le document est en cours de traitement, on l'ignore également.
                if (!processingDocuments.add(entityId)) {
                    log.info("Document {} déjà en cours de traitement.", entityId);
                    continue;
                }

                try {
                    D document = docSnapshot.toObject(getDocumentClass());
                    T entity = toEntity(document);
                    updateDatabase(entity);
                    log.info("📌 [listener][firestore -> local] Persistance de la modification depuis Firestore: [id: {}, collection: {}]", entityId, collectionName);
                } catch (Exception e) {
                    log.error("Erreur lors de la mise à jour de la base locale pour l'ID {}: {}", entityId, e.getMessage());
                    processingDocuments.remove(entityId);
                    continue;
                }

                deleteFromFirestore(entityId);
            }
        });

        log.info("[listener][firebase->local] Écoute des changements Firestore activée pour la collection: '{}'", collectionName);
    }

    /**
     * Supprime un document de Firestore après la synchronisation locale.
     * Une fois supprimé, l'ID est marqué comme traité pour éviter un retraitement.
     * En cas d'erreur, l'ID est retiré de l'ensemble de traitement pour permettre une nouvelle tentative.
     *
     * @param entityId L'ID du document dans Firestore.
     */
    private void deleteFromFirestore(String entityId) {
        DocumentReference docRef = firestore.collection(collectionName).document(entityId);
        ApiFuture<WriteResult> deleteFuture = docRef.delete();
        deleteFuture.addListener(() -> {
            try {
                // Attente de la fin de la suppression.
                deleteFuture.get();
                log.info("🗑️ [firestore] Document supprimé après synchronisation: [id: {}, collection: {}]", entityId, collectionName);
                // On marque le document comme traité.
                processedDocuments.add(entityId);
            } catch (Exception e) {
                log.error("Erreur lors de la suppression du document {}: {}", entityId, e.getMessage());
            } finally {
                // Dans tous les cas, on retire l'ID de l'ensemble des documents en cours.
                processingDocuments.remove(entityId);
            }
        }, executorService);
    }
}
