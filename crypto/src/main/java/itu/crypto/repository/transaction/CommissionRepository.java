package itu.crypto.repository.transaction;

import itu.crypto.entity.commission.Commission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommissionRepository extends JpaRepository<Commission, Integer> {
}
