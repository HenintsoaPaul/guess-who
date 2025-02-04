package itu.crypto.service;

import itu.crypto.entity.Cours;
import itu.crypto.entity.Crypto;
import itu.crypto.enums.CoursAnalysisType;
import itu.crypto.firebase.firestore.generalisation.BaseService;
import itu.crypto.repository.CoursRepository;
import itu.crypto.repository.CryptoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CoursService implements BaseService<Cours> {

    private final CoursRepository coursRepository;
    private final CryptoRepository cryptoRepository;
    private final CryptoService cryptoService;
    private final CryptoPriceGeneratorService cryptoPriceGeneratorService;

    public List<Cours> findAll() {
        return coursRepository.findAll();
    }

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
     * @return a list the current cours after all insertion are done.
     */
    public List<Cours> generateCours() {
        LocalDateTime genTime = LocalDateTime.now();

        List<Cours> currentCours = this.findCurrentCours();
        return currentCours.stream()
                .map(cours -> {
                    Cours c = cryptoPriceGeneratorService.regenerateCours(cours, genTime);
                    coursRepository.save(c);
                    return c;
                }).toList();
    }

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Recuperer les cours compris dans l'intervalle de date.
     * {@code dateMin} et {@code dateMax} sont des bornes nullables.
     */
    public List<Cours> findAllByDateInInterval(LocalDateTime minDate, LocalDateTime maxDate) {
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM cours c WHERE 1=1");
        Map<String, Object> parameters = new HashMap<>();

        if (minDate != null) {
            queryBuilder.append(" AND c.date_cours >= :minDate");
            parameters.put("minDate", minDate);
        }
        if (maxDate != null) {
            queryBuilder.append(" AND c.date_cours <= :maxDate");
            parameters.put("maxDate", maxDate);
        }

        Query query = entityManager.createNativeQuery(queryBuilder.toString(), Cours.class);
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.getResultList();
    }

    /**
     * Recuperer les ventes comprises dans l'intervalle de date.
     * {@code dateMin} et {@code dateMax} sont des bornes nullables.
     */
    public List<Cours> findAllByDateInInterval(String dateMin, String dateMax) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        LocalDateTime dmin = (dateMin == null || dateMin.isEmpty()) ? null : LocalDateTime.parse(dateMin, formatter),
                dmax = (dateMax == null || dateMax.isEmpty()) ? null : LocalDateTime.parse(dateMax, formatter);

        return findAllByDateInInterval(dmin, dmax);
    }

    public List<Crypto> findAllCrypto() {
        return cryptoRepository.findAll();
    }

    public List<Cours> getAnalysis(CoursAnalysisType analysisType, List<Cours> cours) {
        if (analysisType == CoursAnalysisType.MAX_COURS) {
            return findMaxCoursForeachCrypto(cours);
        } else if (analysisType == CoursAnalysisType.MIN_COURS) {
            return findMinCoursForeachCrypto(cours);
        } else if (analysisType == CoursAnalysisType.AVG_COURS) {
            return findAvgCoursForeachCrypto(cours);
        } else if (analysisType == CoursAnalysisType.ECART_TYPE_COURS) {
            return findEcartTypeCoursForeachCrypto(cours);
        } else if (analysisType == CoursAnalysisType.FIRST_QUARTILE_COURS) {
            return findFirstQuartileCoursForeachCrypto(cours);
        }
        return cours;
    }

    private Cours findFirstQuartileCoursCrypto(List<Cours> cours, Crypto crypto) {
        List<Cours> temp = cours.stream()
                .filter(c -> c.getCrypto().equals(crypto))
                .toList();

        // Trier les prix
        List<Double> sortedPrices = temp.stream()
                .mapToDouble(Cours::getPu)
                .sorted()
                .boxed()
                .toList();

        int index = sortedPrices.size() / 4;
        double prix = sortedPrices.get(index);
        // Si l'index est exactement au milieu de deux valeurs
        if (sortedPrices.size() % 4 == 0) {
            prix = (sortedPrices.get(index - 1) + sortedPrices.get(index)) / 2;
        }
        return new Cours(prix, null, crypto);
    }

    private List<Cours> findFirstQuartileCoursForeachCrypto(List<Cours> cours) {
        List<Crypto> cryptos = cours.stream().map(Cours::getCrypto).distinct().toList();
        return cryptos.stream()
                .map(c -> {
                    try {
                        return findFirstQuartileCoursCrypto(cours, c);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    private List<Cours> findEcartTypeCoursForeachCrypto(List<Cours> cours) {
        List<Crypto> cryptos = cours.stream().map(Cours::getCrypto).distinct().toList();
        return cryptos.stream()
                .map(c -> {
                    try {
                        return findStdDevCoursCrypto(cours, c);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    /**
     * Retourne l'ecart-type pour les cours d'un crypto specifique.
     */
    private Cours findStdDevCoursCrypto(List<Cours> cours, Crypto crypto) {
        List<Cours> temp = cours.stream()
                .filter(c -> c.getCrypto().equals(crypto))
                .toList();

        double mean = cryptoService.avg(temp);

        // Calcul de l'écart-type
        double stdDev = Math.sqrt(temp.stream()
                .mapToDouble(Cours::getPu)
                .map(pu -> Math.pow(pu - mean, 2))
                .average()
                .orElse(0.0));

        return new Cours(stdDev, null, crypto);
    }

    private List<Cours> findAvgCoursForeachCrypto(List<Cours> cours) {
        List<Crypto> cryptos = cours.stream().map(Cours::getCrypto).distinct().toList();
        return cryptos.stream()
                .map(c -> {
                    try {
                        return findAvgCoursCrypto(cours, c);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    private Cours findAvgCoursCrypto(List<Cours> cours, Crypto crypto) {
        List<Cours> temp = cours.stream().filter(c -> c.getCrypto().equals(crypto)).toList();
        return new Cours(cryptoService.avg(temp), null, crypto);
    }

    private List<Cours> findMaxCoursForeachCrypto(List<Cours> cours) {
        List<Cours> cbdd = coursRepository.findMaxCoursForeachCrypto(cours);
        cours.retainAll(cbdd);
        return cours;
    }

    private List<Cours> findMinCoursForeachCrypto(List<Cours> cours) {
        List<Cours> cbdd = coursRepository.findMinCoursForeachCrypto(cours);
        cours.retainAll(cbdd);
        return cours;
    }
}
