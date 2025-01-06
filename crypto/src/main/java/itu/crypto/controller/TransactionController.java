package itu.crypto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody DepositRequest request) {
        try {
            transactionService.depot(request.getAccount(), request.getMontant());
            return ResponseEntity.ok("Dépôt effectué avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/retrait")
    public ResponseEntity<String> retrait(@RequestBody WithdrawalRequest request) {
        try {
            transactionService.retrait(request.getAccount(), request.getMontant());
            return ResponseEntity.ok("Retrait effectué avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
