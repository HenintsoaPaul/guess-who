package itu.crypto.service;

import itu.crypto.entity.Transaction;
import itu.crypto.repository.CoursRepository;
import itu.crypto.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public List<Transaction> findAll() {
	return transactionRepository.findAll();
    }

    public Transaction findById(Integer id) throws Exception {
	return transactionRepository.findById(id).orElseThrow(() -> new Exception("Invalid id:" + id));
    }

    @Transactional
    public Transaction save(Transaction transaction) {
	System.out.println("TransactionService.save");
	return null;
    }
}
