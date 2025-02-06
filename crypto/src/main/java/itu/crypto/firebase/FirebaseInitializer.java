package itu.crypto.firebase;

import itu.crypto.entity.Crypto;
import itu.crypto.entity.Purchase;
import itu.crypto.entity.account.Account;
import itu.crypto.entity.cours.Cours;
import itu.crypto.entity.fav.CryptoFav;
import itu.crypto.firebase.firestore.account.AccountSyncService;
import itu.crypto.firebase.firestore.cours.CoursSyncService;
import itu.crypto.firebase.firestore.fav.CryptoFavSyncService;
import itu.crypto.firebase.firestore.fund.MvFundSyncService;
import itu.crypto.firebase.firestore.fund.pending.PendingMvFundSyncService;
import itu.crypto.firebase.firestore.purchase.PurchaseSyncService;
import itu.crypto.repository.CoursRepository;
import itu.crypto.repository.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FirebaseInitializer {

    private final AccountSyncService accountSyncService;
    private final CoursSyncService coursSyncService;
    private final PurchaseSyncService purchaseSyncService;
    private final CryptoFavSyncService cryptoFavSyncService;
    private final MvFundSyncService mvFundSyncService;
    private final PendingMvFundSyncService pendingMvFundSyncService;

    private final AccountRepository accountRepository;
    private final CoursRepository coursRepository;

    @PostConstruct
    public void init() throws Exception {
        log.info("Initializing Firebase");

        testSync();
//        accountSyncService.syncWithFirebase();

//        testGetAll();
//        testSaveAccount();
//        testSaveCours();
    }

    private void testSync() {
//        coursSyncService.syncWithFirebase();
//        purchaseSyncService.syncWithFirebase();
//        cryptoFavSyncService.syncWithFirebase();

//        mvFundSyncService.syncWithFirebase();
        pendingMvFundSyncService.syncWithFirebase();
    }

    private void testSaveAccount() {
        Account account = new Account(null, "fufu", "fufu@gmail.com", "mypassword", 500);
        accountRepository.save(account);
    }

    private void testSaveCours() {
        Cours testListener = new Cours(500, LocalDateTime.now(), new Crypto(1, "Malko", "M"));
        coursRepository.save(testListener);
    }

    public void testGetAll() {
        List<Purchase> purchaseList = purchaseSyncService.getAllEntities();
        System.out.println("Found " + purchaseList.size() + " purchases");

        List<Cours> coursList = coursSyncService.getAllEntities();
        System.out.println("Found " + coursList.size() + " cours");

        List<CryptoFav> cryptoFavList = cryptoFavSyncService.getAllEntities();
        System.out.println("Found " + cryptoFavList.size() + " crypto_fav");
    }
}
