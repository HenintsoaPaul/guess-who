package itu.crypto.service.transaction;

import itu.crypto.entity.purchase.PurchaseException;
import itu.crypto.entity.sale.SaleDetail;
import itu.crypto.entity.account.Account;
import itu.crypto.entity.purchase.Purchase;
import itu.crypto.entity.wallet.Wallet;
import itu.crypto.firebase.firestore.generalisation.BaseService;
import itu.crypto.repository.transaction.PurchaseRepository;
import itu.crypto.repository.transaction.SaleDetailRepository;
import itu.crypto.service.account.AccountService;
import itu.crypto.service.transaction.wallet.WalletService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseService implements BaseService<Purchase> {

    private final PurchaseRepository purchaseRepository;
    private final AccountService accountService;
    private final SaleDetailRepository saleDetailRepository;
    private final WalletService walletService;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Recuperer les ventes comprises dans l'intervalle de date.
     * {@code dateMin} et {@code dateMax} sont des bornes nullables.
     */
    public List<Purchase> findAllByDatePurchaseInRange(String dateMin, String dateMax) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime dmin = (dateMin == null || dateMin.isEmpty()) ? null : LocalDateTime.parse(dateMin + " 00:00:00", formatter),
                dmax = (dateMax == null || dateMax.isEmpty()) ? null : LocalDateTime.parse(dateMax + " 23:59:59", formatter);

        return findAllByDatePurchaseInRange(dmin, dmax);
    }

    /**
     * Recuperer les ventes comprises dans l'intervalle de date.
     * {@code dateMin} et {@code dateMax} sont des bornes nullables.
     */
    public List<Purchase> findAllByDatePurchaseInRange(LocalDateTime minDate, LocalDateTime maxDate) {
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM purchase p WHERE 1=1");
        Map<String, Object> parameters = new HashMap<>();

        if (minDate != null) {
            queryBuilder.append(" AND p.date_purchase >= :minDate");
            parameters.put("minDate", minDate);
        }
        if (maxDate != null) {
            queryBuilder.append(" AND p.date_purchase <= :maxDate");
            parameters.put("maxDate", maxDate);
        }

        Query query = entityManager.createNativeQuery(queryBuilder.toString(), Purchase.class);
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.getResultList();
    }

    @Override
    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    @Override
    public Optional<Purchase> findById(int id) {
        return purchaseRepository.findById(id);
    }

    /**
     * check crypto restant dans wallet avant validation de la vente
     */
    public void controllerAvantInsert(Purchase purchase) throws PurchaseException {
        Wallet wallet = walletService.findByCryptoAndAccount(
                purchase.getSaleDetail().getCrypto().getId(),
                purchase.getAccountSeller().getId()
        ).orElseThrow(
                () -> new PurchaseException("Portfeuille vendeur introuvable.")
        );

        if (wallet.getQuantity() < purchase.getQuantityCrypto()) {
            throw new PurchaseException("Crypto insuffisant dans le portefeuille.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Purchase save(Purchase purchase) throws PurchaseException {
        controllerAvantInsert(purchase);

        return purchaseRepository.save(purchase);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateOrCreate(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(int id) {
        purchaseRepository.deleteById(id);
    }

    public Account findAccountById(Integer id) {
        return accountService.findById(id).orElseThrow();
    }

    public SaleDetail findSaleDetailById(Integer id) {
        return saleDetailRepository.findById(id).orElseThrow();
    }
}
