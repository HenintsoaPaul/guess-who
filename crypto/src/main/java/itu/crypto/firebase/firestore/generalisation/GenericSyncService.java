package itu.crypto.firebase.firestore.generalisation;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class GenericSyncService<T, D> implements ISyncService<T> {

    private final Firestore firestore;
    private final BaseService<T> baseService;
    private final String collectionName;

    protected abstract D toDocument(T entity);
    protected abstract T toEntity(D document);

    public void syncWithFirebase() {
        this.syncWithFirebase(firestore, baseService.findAll());
    }


    @Override
    public void syncWithFirebase(Firestore firestore, List<T> entityList) {
        CollectionReference collectionRef = firestore.collection(collectionName);

        log.info("Debut sync pour la collection '{}'", collectionName);

        for (T entity : entityList) {
            String entityId = getEntityId(entity);

            DocumentReference docRef = collectionRef.document(entityId);
            try {
                DocumentSnapshot snapshot = docRef.get().get();
                if (snapshot.exists()) {
                    D existingDoc = snapshot.toObject(getDocumentClass());
                    T existingEntity = existingDoc == null ? null : toEntity(existingDoc);

                    if (existingEntity != null && !existingEntity.equals(entity)) {
                        updateDocument(docRef, entity, existingDoc);
                    }
                } else {
                    addDocument(docRef, entity);
                }
            } catch (ExecutionException | InterruptedException ex) {
                log.error("Erreur lors de la synchronisation avec Firebase : {}", ex.getMessage());
            }
        }

        log.info("Fin sync pour la collection '{}'", collectionName);

    }

    private void updateDocument(DocumentReference docRef, T entity, D existingDoc) {
        D document = toDocument(entity);
        setUpdatedAt(document);
        docRef.set(document);
        log.info("Mise à jour de l'entité ID: {}", getEntityId(entity));
    }

    private void addDocument(DocumentReference docRef, T entity) {
        D document = toDocument(entity);
        setCreatedAt(document);
        setUpdatedAt(document);
        docRef.set(document);
        log.info("Ajout de l'entité ID: {}", getEntityId(entity));
    }

    private void setCreatedAt(D document) {
        // 🔥 Assure-toi que le document a un champ createdAt
        if (document instanceof TimestampedDocument) {
            ((TimestampedDocument) document).setCreatedAt(Timestamp.now().toString());
        }
    }

    private void setUpdatedAt(D document) {
        // 🔥 Assure-toi que le document a un champ updatedAt
        if (document instanceof TimestampedDocument) {
            ((TimestampedDocument) document).setUpdatedAt(Timestamp.now().toString());
        }
    }

    protected abstract String getEntityId(T entity);

    protected abstract Class<D> getDocumentClass();
}
