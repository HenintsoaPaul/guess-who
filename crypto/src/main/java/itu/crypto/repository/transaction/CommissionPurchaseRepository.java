package itu.crypto.repository.transaction;

import itu.crypto.entity.commission.CommissionPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommissionPurchaseRepository extends JpaRepository<CommissionPurchase, Integer> {
}
