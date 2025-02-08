package itu.crypto.firebase.firestore.generalisation;

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

    // Verification des changements entre doc-entity
    protected boolean hadChanges(T entity, String entityId, BaseService<T> service) {
        if (entityId == null) return true;

        try {
            T existingEntity = service.findById(Integer.parseInt(entityId)).orElse(null);
            if (existingEntity == null) {
                return true;
            }
            return !entity.equals(existingEntity);
        } catch (NumberFormatException e) {
            // if id is a string
            return true;
        }
    }

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

                // Si cet ID a été supprimé par notre code, on l'ignore
                if (deletedDocuments.contains(entityId)) {
                    log.info("Événement REMOVED ignoré pour l'ID {} (suppression initiée par le sync)", entityId);
                    continue;
                }

                D document = docSnapshot.toObject(getDocumentClass());
                System.out.println(document);
                T entity = toEntity(document);
                switch (change.getType()) {
                    case ADDED:
                    case MODIFIED:
                        if (hadChanges(entity, entityId, baseService)) {
                            updateDatabase(entity);
                            log.info("📌 [listener][firestore -> local] Persist change from mobile: [id: {}, collection: {}]", entityId, collectionName);

                            deleteFromFirestore(entityId);
                        }
                        break;

                    case REMOVED:
                        deleteFromDatabase(entityId);
                        log.info("🗑️ [listener][firestore -> local] Suppression de l'entité ID: {}", entityId);
                        break;
                }
            }
        });

        log.info("[listener][firebase->local] Écoute des changements Firestore activée pour la collection: '{}'", collectionName);
    }

    private final Set<String> deletedDocuments = ConcurrentHashMap.newKeySet();

    /**
     * Supprime un document de Firestore après la synchronisation locale.
     * @param entityId L'ID de l'entité dans Firestore.
     */
    private void deleteFromFirestore(String entityId) {
        deletedDocuments.add(entityId);

        DocumentReference docRef = firestore.collection(collectionName).document(entityId);
        docRef.delete().addListener(() -> {
            log.info("🗑️ [firestore] Document supprimé après synchronisation: [id: {}, collection: {}]", entityId, collectionName);
            deletedDocuments.remove(entityId);
        }, executorService);
    }
}
