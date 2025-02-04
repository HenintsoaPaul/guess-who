package itu.crypto.firebase;

import itu.crypto.firebase.firestore.cours.CoursSyncService;
import itu.crypto.firebase.firestore.purchase.PurchaseSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class FirebaseInitializer {

    private final CoursSyncService coursSyncService;
    private final PurchaseSyncService purchaseSyncService;

    @PostConstruct
    public void init() throws Exception {
        System.out.println("Initializing Firebase");


        // Appeler la méthode de synchronisation
        coursSyncService.syncWithFirebase();

        purchaseSyncService.syncWithFirebase();
    }
}
