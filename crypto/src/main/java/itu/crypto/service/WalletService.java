package itu.crypto.service;

import itu.crypto.dto.WalletDTO;
import itu.crypto.repository.WalletRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
                (Integer) row[4]                          // quantity
            );

            wallets.add(wallet);
        }

        return wallets;
    }

    @GetMapping("/wallets/{id}")
    public List<WalletDTO> getWalletByUser(@PathVariable int id) {
        List<Object[]> results = walletRepository.findWalletByUser(id);
        return this.mapToWalletDetailsDTO(results);
    }
}
