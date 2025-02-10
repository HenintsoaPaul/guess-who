package itu.crypto.repository.transaction.fund;

import itu.crypto.entity.fund.PendingMvFund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PendingMvFundRepository extends JpaRepository<PendingMvFund, Integer> {

    @Query(value = """
            select pmf from PendingMvFund pmf
                        where pmf.dateValidation is null
                                    and pmf.pendingState.id = 1
            """)
    List<PendingMvFund> findAllAttente();

    @Query(value = """
            select pmf from PendingMvFund pmf
                        where pmf.account.id = :idAccount
            """)
    List<PendingMvFund> findAllByUser(int idAccount);
}
