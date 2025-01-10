package itu.crypto.service;

import itu.crypto.entity.Cours;
import itu.crypto.entity.Crypto;
import itu.crypto.repository.CoursRepository;
import itu.crypto.repository.CryptoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CoursService {
    private final CoursRepository coursRepository;
    private final CryptoRepository cryptoRepository;

    public List<Cours> findCurrentCours() {
        List<Cours> o = null;
        return o;
    }

    public List<Cours> generateCours() {
        List<Crypto> cryptos = cryptoRepository.findAll();
        for (Crypto crypto : cryptos) {
            Cours cours = new Cours();
            cours.setCrypto(crypto);
            cours.setDateCours(LocalDate.now());
            cours.setPu(generateRandomPrice());
            coursRepository.save(cours);
        }
        return coursRepository.findAll();
    }

    private double generateRandomPrice() {
        Random random = new Random();
        return 50 + (1000 - 50) * random.nextDouble();
    }
}
