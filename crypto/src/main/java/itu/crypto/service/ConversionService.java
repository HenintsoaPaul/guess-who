package itu.crypto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itu.crypto.entity.*;
import itu.crypto.repository.*;

@Service
public class ConversionService {

    @Autowired
    private CryptoRepository cryptoRepository;

    @Autowired
    private DollarRepository dollarRepository;

    public void convertCryptoToDollar(Crypto crypto, int quantity) {
        Dollar dollar = new Dollar();
        dollar.setAmount(amount);
        dollar.setCurrency("USD"); 
        dollar.setTimestamp(LocalDateTime.now()); 
        
        dollarRepository.save(dollar);
        
        crypto.setAmount(crypto.getAmount() - amount);
        cryptoRepository.save(crypto);
    }

    public void convertDollarToCrypto(Dollar dollar, double amount) {
        Crypto crypto = new Crypto();
        crypto.setAmount(amount);
        crypto.setCurrency("CRYPTO"); 
        crypto.setTimestamp(LocalDateTime.now()); 
        
        cryptoRepository.save(crypto);
        
        dollar.setAmount(dollar.getAmount() - amount);
        dollarRepository.save(dollar);
    }

    public double getExchangeRate() {
        // Cette méthode devrait retourner le taux de change actuel entre Crypto et Dollar
        // Dans une implémentation réelle, vous pourriez utiliser des API d'échange ou stocker les données historiques
        return 1.0; // Exemple : 1 Crypto = 1 Dollar
    }
}
