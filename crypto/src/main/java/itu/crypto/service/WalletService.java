package itu.crypto.service;

import itu.crypto.dto.WalletDTO;
import itu.crypto.repository.WalletRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.*;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public List<WalletDTO> mapToWalletDetailsDTO(List<Object[]> results) {
        List<WalletDTO> wallets = new ArrayList<>();

        for (Object[] row : results) {
            WalletDTO wallet = new WalletDTO(
                (Integer) row[0],                       // id_account
                (String) row[1],                         // pseudo
                ((BigDecimal) row[2]).doubleValue(),  // fund
                (String) row[3],                          // crypto_name
                (Integer) row[4] ,
                null,  
                null                        
            );

            wallets.add(wallet);
        }

        return wallets;
    }

    public List<WalletDTO> getWalletByUser(@PathVariable int id) {
        List<Object[]> results = walletRepository.findWalletByUser(id);
        return this.mapToWalletDetailsDTO(results);
    }

    public List<WalletDTO> getPurchaseHistory(int id) {
        try {
            List<Object[]> results = walletRepository.findPurchaseHistoryByUserId(id);
            
            return mapToPurchaseHistoryDTO(results);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch purchase history", e);
        }
    }

    private List<WalletDTO> mapToPurchaseHistoryDTO(List<Object[]> results) {
        return results.stream()
                .map(row -> new WalletDTO(
                        null,  // idAccount n'est pas utilisé ici
                        null,  // pseudo n'est pas utilisé ici
                        null,  // fund n'est pas utilisé ici
                        (String) row[1],  // cryptoName
                        (Integer) row[0],  // quantity
                        row[2] instanceof java.sql.Date ? 
                            (java.sql.Date) row[2] : 
                            new java.sql.Date(((Timestamp) row[2]).getTime()),
                        ((BigDecimal) row[3] != null ? ((BigDecimal) row[3]).doubleValue() : null)
                ))
                .collect(Collectors.toList());
    }
    
}
