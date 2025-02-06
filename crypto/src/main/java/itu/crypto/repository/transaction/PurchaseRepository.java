package itu.crypto.repository.transaction;

import itu.crypto.entity.purchase.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

/*
    @Query(value = """
            SELECT * FROM purchase p
            WHERE (p.date_purchase >= COALESCE(:minDate, CAST(NULL AS timestamp)) OR :minDate IS NULL)
              AND (p.date_purchase <= COALESCE(:maxDate, CAST(NULL AS timestamp)) OR :maxDate IS NULL)
            """, nativeQuery = true)
    List<Purchase> findAllByDatePurchaseInRange(LocalDateTime minDate, LocalDateTime maxDate);
*/

}