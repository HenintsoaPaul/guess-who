package itu.crypto.service;

import itu.crypto.entity.cours.Cours;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class CryptoPriceGeneratorService {
    private static final Random random = new Random();

    /**
     * Génère un nouveau prix basé sur le prix précédent avec des variations réalistes
     */
    private double generateNextPrice(double currentPrice) {
        // Variation quotidienne typique entre -5% et +5%
        double maxVariation = 0.05;
        double variation = random.nextDouble() * maxVariation * 2 - maxVariation;

        // Ajout de volatilité aléatoire
        double volatilityFactor = 1 + random.nextDouble() * 0.5;

        return currentPrice * (1 + variation * volatilityFactor);
    }

    public Cours regenerateCours(Cours cours, LocalDateTime gendate) {
        return new Cours(generateNextPrice(cours.getPu()), gendate, cours.getCrypto());
    }
}