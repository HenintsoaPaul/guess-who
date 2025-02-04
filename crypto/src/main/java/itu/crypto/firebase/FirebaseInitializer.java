package itu.crypto.firebase;

import itu.crypto.entity.Crypto;
import itu.crypto.entity.cours.Cours;
import itu.crypto.firebase.firestore.cours.CoursSyncService;
import itu.crypto.firebase.firestore.fav.CryptoFavSyncService;
import itu.crypto.firebase.firestore.purchase.PurchaseSyncService;
import itu.crypto.repository.CoursRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class FirebaseInitializer {

    private final CoursSyncService coursSyncService;
    private final PurchaseSyncService purchaseSyncService;
    private final CryptoFavSyncService cryptoFavSyncService;

    private final CoursRepository coursRepository;

    @PostConstruct
    public void init() throws Exception {
        System.out.println("Initializing Firebase");

//        Cours testListener = new Cours(500, LocalDateTime.now(), new Crypto(1, "Malko", "M"));
//        coursRepository.save(testListener);


        // Appeler la méthode de synchronisation
//        coursSyncService.syncWithFirebase();

//        purchaseSyncService.syncWithFirebase();

//        cryptoFavSyncService.syncWithFirebase();
    }
}
