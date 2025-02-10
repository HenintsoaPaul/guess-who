package itu.crypto.repository.transaction.fund;

import itu.crypto.entity.fund.TypeMvFund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeMvFundRepository extends JpaRepository<TypeMvFund, Integer> {
}
