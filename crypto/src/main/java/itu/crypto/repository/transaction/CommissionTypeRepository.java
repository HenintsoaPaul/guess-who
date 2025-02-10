package itu.crypto.repository.transaction;

import itu.crypto.entity.commission.CommissionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommissionTypeRepository extends JpaRepository<CommissionType, Integer> {
}
