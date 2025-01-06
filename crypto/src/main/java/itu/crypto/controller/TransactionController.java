package itu.crypto.controller;

import itu.crypto.entity.Account;
import itu.crypto.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import itu.crypto.dto.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/depot")
    public String deposit(@RequestBody DepositRequest request) {
        try {
            Account account = request.getAccount();
            transactionService.Depot(
                account,
                request.getMontant(),
                request.getDate(),
                request.getQuantity()
            );
            return "Dépôt effectué avec succès pour le compte ID : " + account.getId();
        } catch (Exception e) {
            return "Erreur lors du dépôt : " + e.getMessage();
        }
    }

    @PostMapping("/retrait")
    public String withdraw(@RequestBody DepositRequest request) {
        try {
            Account account = request.getAccount();
            transactionService.Retait(
                account,
                request.getMontant(),
                request.getDate(),
                request.getQuantity()
            );
            return "Retrait effectué avec succès pour le compte ID : " + account.getId();
        } catch (Exception e) {
            return "Erreur lors du retrait : " + e.getMessage();
        }
    }
}