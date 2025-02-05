package itu.crypto.firebase.firestore.cours;

import com.google.cloud.firestore.Firestore;
import itu.crypto.entity.cours.Cours;
import itu.crypto.firebase.firestore.generalisation.GenericSyncService;
import itu.crypto.service.CoursService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
