package itu.crypto.service;

import itu.crypto.entity.Transaction;
import itu.crypto.entity.TransactionDetail;
import itu.crypto.repository.TransactionDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionDetailService {
    private final TransactionDetailRepository transactionDetailRepository;

    public List<TransactionDetail> findAllByTransaction(Transaction transaction) {
	return transactionDetailRepository.findAllByTransaction(transaction);
    }
}
