package itu.crypto.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import itu.crypto.entity.*;
import itu.crypto.repository.*;
import java.sql.*;

public class TransactionService {
    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    private final MvFundRepository MvFundRepository;

    public void Depot(Account account, double montant, Date date, int quantity ) {
        double fund = account.getFund();

        TypeMvFund type = new TypeMvFund("Depot");
        MvFund mvFund = new MvFund(date, quantity, type, account);
        MvFundRepository.save(mvFund);

        account.setFund(account.getFund() + montant);

        accountRepository.save(account);
    }

    public void Retait(Account account, double montant, Date date, int quantity ) {
        double fund = account.getFund();

        TypeMvFund type = new TypeMvFund("Retrait");
        MvFund mvFund = new MvFund(date, quantity, type, account);
        MvFundRepository.save(mvFund);

        account.setFund(account.getFund() + montant);

        accountRepository.save(account);
    }
}
