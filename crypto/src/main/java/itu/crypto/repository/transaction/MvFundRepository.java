package itu.crypto.repository.transaction;

import itu.crypto.entity.MvFund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MvFundRepository extends JpaRepository<MvFund, Integer> {
}
