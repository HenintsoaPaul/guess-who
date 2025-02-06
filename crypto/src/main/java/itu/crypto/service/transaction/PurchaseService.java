package itu.crypto.service.transaction;

import itu.crypto.entity.purchase.Purchase;
import itu.crypto.firebase.firestore.generalisation.BaseService;
import itu.crypto.repository.transaction.PurchaseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PurchaseService implements BaseService<Purchase> {

    private final PurchaseRepository purchaseRepository;

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

    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }
}
