package itu.crypto.firebase;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import itu.crypto.entity.Cours;
import itu.crypto.firebase.firestore.cours.CoursDocument;
import itu.crypto.service.CoursService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoursSyncService implements ISyncService<Cours> {

    private final Firestore firestore;
    private final CoursService coursService;

    public String syncWithFirebase() {
        return this.syncWithFirebase(firestore, coursService.findAll());
    }

    @Override
    public String syncWithFirebase(Firestore firestore, List<Cours> coursList) {
        CollectionReference coursCollection = firestore.collection("cours");

        for (Cours cours : coursList) {
            String coursId = cours.getId().toString(); // Use local ID as firestore ID

            DocumentReference coursRef = coursCollection.document(coursId);
            try {
                DocumentSnapshot snapshot = coursRef.get().get();
                if (snapshot.exists()) {
                    CoursDocument existingCoursDocument = snapshot.toObject(CoursDocument.class);
                    Cours existingCours = existingCoursDocument == null ?
                            null : existingCoursDocument.toEntity();

                    // Compare and update if needed
                    if (existingCours != null && !existingCours.equals(cours)) {
                        log.info("Update du cours: {}", coursId);
                        coursRef.set(cours);
                    }
                } else {
                    // Add new document
                    coursRef.set(new CoursDocument(cours));
                }
            } catch (ExecutionException | InterruptedException ex) {
                log.error("Erreur lors de la sync des cours vers Firebase : {}", ex.getMessage());
            }
        }

        return "";
    }
}
