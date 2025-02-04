package itu.crypto.firebase.firestore.cours;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import itu.crypto.entity.cours.Cours;
import itu.crypto.firebase.firestore.generalisation.GenericSyncService;
import itu.crypto.service.CoursService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class CoursSyncService extends GenericSyncService<Cours, CoursDocument> {

    public CoursSyncService(Firestore firestore, CoursService coursService) {
        super(firestore, coursService, "cours");
    }

    @Override
    protected CoursDocument toDocument(Cours entity) {
        return new CoursDocument(entity);
    }

    @Override
    protected Cours toEntity(CoursDocument document) {
        return document.toEntity();
    }

    @Override
    protected String getEntityId(Cours entity) {
        return entity.getId().toString();
    }

    @Override
    protected Class<CoursDocument> getDocumentClass() {
        return CoursDocument.class;
    }

    public void saveAsDocument(Cours entity) throws ExecutionException, InterruptedException {
        try {
            CollectionReference collectionRef = firestore.collection(collectionName);

            String entityId = entity.getId().toString();

            CoursDocument document = new CoursDocument(entity);

            DocumentReference docRef = collectionRef.document(entityId);
            docRef.set(document).get(); // `get()` pour attendre la fin de l'opération

            log.info("Add document id '{}' in collection '{}'", entityId, collectionName);
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error on document insertion in collection '{}'. Error: {}", collectionName, e.getMessage());
            throw e;
        }
    }

    @Deprecated
    public void updateAsDocument(Cours cours) {
        // todo,,,,
    }
}
