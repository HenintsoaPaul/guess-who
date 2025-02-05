package itu.crypto.firebase;

import itu.crypto.entity.Purchase;
import itu.crypto.entity.cours.Cours;
import itu.crypto.entity.fav.CryptoFav;
import itu.crypto.firebase.firestore.cours.CoursSyncService;
import itu.crypto.firebase.firestore.fav.CryptoFavSyncService;
import itu.crypto.firebase.firestore.purchase.PurchaseSyncService;
import itu.crypto.repository.CoursRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.List;

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

        List<Purchase> purchaseList = purchaseSyncService.getAllEntities();
        System.out.println("Found " + purchaseList.size() + " purchases");

        List<Cours> coursList = coursSyncService.getAllEntities();
        System.out.println("Found " + coursList.size() + " cours");

        List<CryptoFav> cryptoFavList = cryptoFavSyncService.getAllEntities();
        System.out.println("Found " + cryptoFavList.size() + " crypto_fav");

//        Cours testListener = new Cours(500, LocalDateTime.now(), new Crypto(1, "Malko", "M"));
//        coursRepository.save(testListener);


        // Appeler la méthode de synchronisation
//        coursSyncService.syncWithFirebase();

//        purchaseSyncService.syncWithFirebase();

//        cryptoFavSyncService.syncWithFirebase();
    }
}
