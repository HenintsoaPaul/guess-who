package itu.crypto.repository.transaction;

import itu.crypto.entity.MvFund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MvFundRepository extends JpaRepository<MvFund, Integer> {
}
