package itu.crypto.repository.transaction.fund;

import itu.crypto.entity.fund.MvFund;
import itu.crypto.entity.fund.PendingMvFund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MvFundRepository extends JpaRepository<MvFund, Integer> {
    MvFund findByPendingMvFund(PendingMvFund pendingMvFund);
}
