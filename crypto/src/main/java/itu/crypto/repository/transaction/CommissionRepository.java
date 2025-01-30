package itu.crypto.repository.transaction;

import itu.crypto.entity.commission.Commission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommissionRepository extends JpaRepository<Commission, Integer> {

    @Query(value = """
            select c from Commission c
                        where c.commissionType.id = :idCommissionType 
                                    order by c.addDate desc limit 1
            """)
    Commission findLatestByType(Integer idCommissionType);
}
