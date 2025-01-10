package itu.crypto.service;

import itu.crypto.entity.Cours;
import itu.crypto.entity.Crypto;
import itu.crypto.repository.CoursRepository;
import itu.crypto.repository.CryptoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoursService {
    private final CoursRepository coursRepository;
    private final CryptoRepository cryptoRepository;

    /**
     * Retrieves the latest course for each crypto.
     *
     * @return a list of the latest courses for each crypto.
     */
    public List<Cours> findCurrentCours() {
        return coursRepository.findLatestCoursForEachCrypto();
    }

    /**
     * Generates a list of Cours objects for all available Crypto entities.
     * For each Crypto, a new Cours object is created with the current date and a random price.
     * The generated Cours objects are then saved to the repository.
     *
     * @return a list of all Cours objects saved in the repository.
     */
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
