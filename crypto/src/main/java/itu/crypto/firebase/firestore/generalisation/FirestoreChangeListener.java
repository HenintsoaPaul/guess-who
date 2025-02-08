package itu.crypto.firebase.firestore.generalisation;

import com.google.cloud.firestore.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class FirestoreChangeListener<T, D> {

    private final Firestore firestore;
    private final BaseService<T> baseService;
    @Getter
    private final String collectionName;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    protected abstract T toEntity(D document);

    protected abstract Class<D> getDocumentClass();

    protected abstract void updateDatabase(T entity);

    protected abstract void deleteFromDatabase(String entityId);

    // Verification des changements entre doc-entity
    protected boolean hadChanges(T entity, String entityId, BaseService<T> service) {
        T existingEntity = service.findById(Integer.parseInt(entityId)).orElse(null);
        if (existingEntity == null) {
            return true;
        }
        return !entity.equals(existingEntity);
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

                D document = docSnapshot.toObject(getDocumentClass());
                T entity = toEntity(document);
                switch (change.getType()) {
                    case ADDED:
                    case MODIFIED:
                        if (hadChanges(entity, entityId, baseService)) {
                            updateDatabase(entity);
                            log.info("📌 [listener][firestore -> local] Add/Update: [id: {}, collection: {}]", entityId, collectionName);
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
}
