package itu.crypto.firebase;

import com.google.cloud.firestore.Firestore;
import itu.crypto.service.CoursService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class FirebaseInitializer {

    private final CoursSyncService coursSyncService;
    private final CoursService coursService;

    private final Firestore firestore;

    @PostConstruct
    public void init() throws Exception {
        System.out.println("Initializing Firebase");


        // Appeler la méthode de synchronisation
        coursSyncService.syncWithFirebase();
    }
}
