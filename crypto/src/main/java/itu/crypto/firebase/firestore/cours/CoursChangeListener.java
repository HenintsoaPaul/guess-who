package itu.crypto.firebase.firestore.cours;

import com.google.cloud.firestore.Firestore;
import itu.crypto.entity.cours.Cours;
import itu.crypto.firebase.firestore.FirestoreChangeListener;
import itu.crypto.service.CoursService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CoursChangeListener extends FirestoreChangeListener<Cours, CoursDocument> {

    private final CoursService coursService;

    public CoursChangeListener(Firestore firestore, CoursService coursService) {
        super(firestore, coursService, "cours");
        this.coursService = coursService;
    }

    @Override
    protected Cours toEntity(CoursDocument document) {
        return document.toEntity();
    }

    @Override
    protected Class<CoursDocument> getDocumentClass() {
        return CoursDocument.class;
    }

    @Override
    protected void updateDatabase(Cours entity) {
        coursService.updateOrCreate(entity); // 🔥 Mise à jour ou insertion
    }

    @Override
    protected void deleteFromDatabase(String entityId) {
        coursService.deleteById(Integer.parseInt(entityId)); // 🔥 Suppression de la ligne
    }

    protected boolean hadChanges(Cours entity) {
        return false;
    }
}
