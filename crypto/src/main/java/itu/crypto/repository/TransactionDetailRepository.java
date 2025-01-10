package itu.crypto.repository;

import itu.crypto.entity.Transaction;
import itu.crypto.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Integer> {
    List<TransactionDetail> findAllByTransaction(Transaction transaction);
}
