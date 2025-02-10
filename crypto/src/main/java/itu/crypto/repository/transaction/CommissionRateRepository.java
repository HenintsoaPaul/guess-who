package itu.crypto.repository.transaction;

import itu.crypto.entity.commission.CommissionRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommissionRateRepository extends JpaRepository<CommissionRate, Integer> {

    @Query(value = """
            select c from CommissionRate c
                        where c.commissionType.id = :idCommissionType
                                    order by c.addDate desc limit 1
            """)
    CommissionRate findLatestByType(Integer idCommissionType);
}
