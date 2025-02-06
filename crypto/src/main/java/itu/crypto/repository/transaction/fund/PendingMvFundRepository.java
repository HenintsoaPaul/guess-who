package itu.crypto.repository.transaction.fund;

import itu.crypto.entity.fund.PendingMvFund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingMvFundRepository extends JpaRepository<PendingMvFund, Integer> {
}
