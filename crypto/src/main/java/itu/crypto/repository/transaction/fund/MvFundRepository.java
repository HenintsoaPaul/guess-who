package itu.crypto.repository.transaction.fund;

import itu.crypto.entity.fund.MvFund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MvFundRepository extends JpaRepository<MvFund, Integer> {
    @Query(value = """
                select mf from MvFund mf where mf.pendingMvFund.id = :idPendingMvFund
            """)
    Optional<MvFund> findByIdPendingMvFund(int idPendingMvFund);
}
