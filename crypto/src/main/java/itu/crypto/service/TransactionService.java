package itu.crypto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import itu.crypto.entity.*;
import itu.crypto.repository.*;
import java.sql.*;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final MvFundRepository mvFundRepository;

    @Autowired
    public TransactionService(AccountRepository accountRepository, MvFundRepository mvFundRepository) {
        this.accountRepository = accountRepository;
        this.mvFundRepository = mvFundRepository;
    }

    public void Depot(Account account, double montant, Date date, int quantity) {
        double fund = account.getFund();

        TypeMvFund type = new TypeMvFund("Depot");
        MvFund mvFund = new MvFund(date, quantity, type, account);
        mvFundRepository.save(mvFund);

        double nvfund = fund + montant;
        account.setFund(nvfund);

        accountRepository.save(account);
    }

    public void Retait(Account account, double montant, Date date, int quantity) {
        double fund = account.getFund();

        TypeMvFund type = new TypeMvFund("Retrait");
        MvFund mvFund = new MvFund(date, quantity, type, account);
        mvFundRepository.save(mvFund);

        double nvfund = fund - montant;
        account.setFund(nvfund);

        accountRepository.save(account);
    }
}
