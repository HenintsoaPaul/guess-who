package itu.crypto.service;

import itu.crypto.entity.Cours;
import itu.crypto.entity.Crypto;
import itu.crypto.repository.CoursRepository;
import itu.crypto.repository.CryptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversionService {

    private final CryptoRepository cryptoRepository;
    private final CoursRepository coursRepository;

    @Autowired
    public ConversionService(CryptoRepository cryptoRepository, CoursRepository coursRepository) {
        this.cryptoRepository = cryptoRepository;
        this.coursRepository = coursRepository;
    }

    public double convertCryptoToDollar(String symbol, double amountCrypto) {
        Crypto crypto = cryptoRepository.findBySymbol(symbol)
                .orElseThrow(() -> new IllegalArgumentException("Cryptomonnaie introuvable : " + symbol));

        Cours latestCours = coursRepository.findLatestCoursByIdCrypto(crypto.getId())
                .orElseThrow(() -> new IllegalStateException("Cours introuvable pour : " + symbol));

        return amountCrypto * latestCours.getPu();  
    }

    public double convertDollarToCrypto(String symbol, double amountDollar) {
        Crypto crypto = cryptoRepository.findBySymbol(symbol)
                .orElseThrow(() -> new IllegalArgumentException("Cryptomonnaie introuvable : " + symbol));

        Cours latestCours = coursRepository.findLatestCoursByIdCrypto(crypto.getId())
                .orElseThrow(() -> new IllegalStateException("Cours introuvable pour : " + symbol));

        return amountDollar / latestCours.getPu();  
    }
}
