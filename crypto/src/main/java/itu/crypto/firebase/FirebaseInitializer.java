package itu.crypto.firebase;

import itu.crypto.entity.purchase.Purchase;
import itu.crypto.entity.account.Account;
import itu.crypto.entity.cours.Cours;
import itu.crypto.entity.crypto.CryptoFav;
import itu.crypto.entity.wallet.Wallet;
import itu.crypto.firebase.firestore.FirestoreChangeListenerManager;
import itu.crypto.firebase.firestore.FirestoreSyncManager;
import itu.crypto.firebase.firestore.account.AccountSyncService;
import itu.crypto.firebase.firestore.cours.CoursSyncService;
import itu.crypto.firebase.firestore.crypto.CryptoFavSyncService;
import itu.crypto.firebase.firestore.fund.MvFundSyncService;
import itu.crypto.firebase.firestore.purchase.PurchaseSyncService;
import itu.crypto.firebase.firestore.wallet.WalletSyncService;
import itu.crypto.repository.CoursRepository;
import itu.crypto.repository.CryptoFavRepository;
import itu.crypto.repository.CryptoRepository;
import itu.crypto.repository.account.AccountRepository;
import itu.crypto.repository.transaction.SaleDetailRepository;
import itu.crypto.repository.transaction.wallet.WalletRepository;
import itu.crypto.service.transaction.PurchaseService;
import itu.crypto.service.transaction.fund.MvFundService;
import itu.crypto.service.transaction.fund.PendingMvFundService;
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

    private final FirestoreChangeListenerManager firestoreChangeListenerManager;
    private final FirestoreSyncManager firestoreSyncManager;

    private final AccountSyncService accountSyncService;
    private final CoursSyncService coursSyncService;
    private final PurchaseSyncService purchaseSyncService;
    private final CryptoFavSyncService cryptoFavSyncService;
    private final MvFundSyncService mvFundSyncService;
    private final PendingMvFundSyncService pendingMvFundSyncService;
    private final WalletSyncService walletSyncService;

    private final AccountRepository accountRepository;
    private final CoursRepository coursRepository;
    private final CryptoRepository cryptoRepository;
    private final CryptoFavRepository cryptoFavRepository;
    private final MvFundService mvFundService;
    private final PendingMvFundService pendingMvFundService;

    private final PurchaseService purchaseService;

    private final WalletRepository walletRepository;
    private final SaleDetailRepository saleDetailRepository;

    @PostConstruct
    public void init() {
         firestoreSyncManager.init();
         firestoreChangeListenerManager.init();
    }

    private void testAccount() {
        accountSyncService.syncWithFirebase();

        Account account = new Account(null, "fufu", "fufu@gmail.com", "mypassword", 500.00),
                vao = accountRepository.save(account);

        vao.setEmail("blabla.com");
        accountRepository.save(vao);
    }

    private void testCours() {
        coursSyncService.syncWithFirebase();

        Cours vao = coursRepository.save(new Cours(
                null,
                52,
                LocalDateTime.now(),
                cryptoRepository.findAll().get(0)
        ));

        Cours w = coursRepository.findById(vao.getId()).orElse(null);
        w.setPu(55555);
        coursRepository.save(w);
    }

    private void testCryptoFav() {
        cryptoFavSyncService.syncWithFirebase();

        LocalDateTime zao = LocalDateTime.now();

        CryptoFav vao = cryptoFavRepository.save(new CryptoFav(
                null,
                cryptoRepository.findAll().get(0),
                accountRepository.findAll().get(0),
                zao,
                true
        ));

        CryptoFav w = cryptoFavRepository.findById(1).orElse(null);
        w.setDateCryptoFav(zao);
        cryptoFavRepository.save(w);
    }

    private void testWallet() {
        walletSyncService.syncWithFirebase();

        Wallet vao = walletRepository.save(new Wallet(
                null,
                52,
                cryptoRepository.findAll().get(0),
                accountRepository.findAll().get(0)
        ));

        Wallet w = walletRepository.findById(vao.getId()).orElse(null);
        w.setQuantity(55555);
        walletRepository.save(w);
    }

    private void testPendingMvFund() {
        pendingMvFundService.save(pendingMvFundService.cobaieAttente());
        pendingMvFundService.save(pendingMvFundService.cobaieAttente());
        System.out.println("\n");
        pendingMvFundService.save(pendingMvFundService.cobaieValide());
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
