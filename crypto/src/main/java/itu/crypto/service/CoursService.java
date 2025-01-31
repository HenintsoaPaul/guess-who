package itu.crypto.service;

import itu.crypto.entity.Cours;
import itu.crypto.entity.Crypto;
import itu.crypto.enums.CoursAnalysisType;
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
public class CoursService {

    private final CoursRepository coursRepository;
    private final CryptoRepository cryptoRepository;

    /**
     * Retrieves the latest course for each crypto.
     *
     * @return a list of the latest courses for each crypto.
     */
    @Deprecated
    public List<Cours> findCurrentCours() throws Exception {
//        return coursRepository.findLatestCoursForEachCrypto();
        throw new Exception("Not Impleemented");
    }

    /**
     * Generates a list of Cours objects for all available Crypto entities.
     * For each Crypto, a new Cours object is created with the current date and a random price.
     * The generated Cours objects are then saved to the repository.
     *
     * @return a list of all Cours objects saved in the repository.
     */
    public List<Cours> generateCours() {
        LocalDateTime genTime = LocalDateTime.now();
        cryptoRepository.findAll().forEach(crypto -> coursRepository.save(new Cours(generateRandomPrice(), genTime, crypto)));
        return coursRepository.findAll();
    }

    private double generateRandomPrice() {
        Random random = new Random();
        double MIN_SEUIL_PRICE = 50;
        double MAX_SEUIL_PRICE = 1000;
        return MIN_SEUIL_PRICE + (MAX_SEUIL_PRICE - MIN_SEUIL_PRICE) * random.nextDouble();
    }


    public List<Cours> findAll() {
        return coursRepository.findAll();
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
//            return findAvgCoursForeachCrypto(cours);
        } else if (analysisType == CoursAnalysisType.ECART_TYPE_COMMISSION) {
//            return findEcartTypeCoursForeachCrypto(cours);
        } else if (analysisType == CoursAnalysisType.FIRST_QUARTILE_COMMISSION) {
//            return findFirstQuartileCoursForeachCrypto(cours);
        }
        return cours;
    }

//    private List<Cours> findAvgCoursForeachCrypto(List<Cours> cours) {
//        List<Crypto> cryptos = cours.stream()
//                .map(Cours::getCrypto)
//                .distinct()
//                .toList();
//        return cryptos.stream()
//                .map(c -> findAvgCoursCrypto(cours, c))
//                .toList();
//    }
//
//    private Cours findAvgCoursCrypto(List<Cours> cours, Crypto crypto) {
//        List<Cours> temp = cours.stream()
//                .filter(c -> c.getCrypto().equals(crypto))
//                .toList();
//        double somme = temp.stream()
//                .mapToDouble(Cours::getPu)
//                .sum();
//        return new Cours(somme / temp.size(), null, crypto);
//    }

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
