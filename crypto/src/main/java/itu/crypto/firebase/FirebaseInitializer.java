package itu.crypto.firebase;

import com.google.firebase.messaging.FirebaseMessagingException;
import itu.crypto.entity.purchase.Purchase;
import itu.crypto.entity.account.Account;
import itu.crypto.entity.cours.Cours;
import itu.crypto.entity.crypto.CryptoFav;
import itu.crypto.entity.wallet.Wallet;
import itu.crypto.firebase.firestore.account.AccountSyncService;
import itu.crypto.firebase.firestore.cours.CoursSyncService;
import itu.crypto.firebase.firestore.fav.CryptoFavSyncService;
import itu.crypto.firebase.firestore.fund.MvFundSyncService;
import itu.crypto.firebase.firestore.fund.pending.PendingMvFundSyncService;
import itu.crypto.firebase.firestore.purchase.PurchaseSyncService;
import itu.crypto.firebase.firestore.wallet.WalletSyncService;
import itu.crypto.repository.CoursRepository;
import itu.crypto.repository.CryptoFavRepository;
import itu.crypto.repository.CryptoRepository;
import itu.crypto.repository.account.AccountRepository;
import itu.crypto.repository.transaction.PurchaseRepository;
import itu.crypto.repository.transaction.SaleDetailRepository;
import itu.crypto.repository.transaction.wallet.WalletRepository;
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
    private final PurchaseRepository purchaseRepository;
    private final WalletRepository walletRepository;
    private final SaleDetailRepository saleDetailRepository;

    @PostConstruct
    public void init() throws Exception {
        log.info("Initializing Firebase begins...");

//        testAccount();
//        testCours();
//        testCryptoFav();
//        testPurchase();

        log.info("Firebase initialization complete.");
    }

    private void testAccount() {
        accountSyncService.syncWithFirebase();

        Account account = new Account(null, "fufu", "fufu@gmail.com", "mypassword", 500),
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
                zao
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

    private void testPurchase() {
        purchaseSyncService.syncWithFirebase();

        Purchase p = new Purchase(
                null,
                LocalDateTime.now(),
                1500.00,
                500.00,
                3,
                accountRepository.findAll().get(0),
                accountRepository.findAll().get(1),
                saleDetailRepository.findAll().get(0)
        );
        Purchase vao = purchaseRepository.save(p);

        Purchase w = purchaseRepository.findById(vao.getId()).orElse(null);
        w.setQuantityCrypto(55555);
        purchaseRepository.save(w);
    }

    private void testPendingMvFund() throws FirebaseMessagingException {
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
