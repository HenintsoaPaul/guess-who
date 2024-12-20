package itu.gestionrh.service;

import itu.gestionrh.model.Employe;
import itu.gestionrh.repository.EmployeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class EmployeService {
    private final EmployeRepository employeRepository;

    public EmployeService(EmployeRepository employeRepository) {
        this.employeRepository = employeRepository;
    }

    public List<Employe> getAllEmployes() {
        return employeRepository.findAll();
    }

    // Calcul de l'ancienneté
    public long calculateAnciennete(LocalDate dateEmbauche) {
        LocalDate today = LocalDate.now();
        return ChronoUnit.YEARS.between(dateEmbauche, today);
    }

    public long calculateAnciennete(String dateEmbaucheStr) {
        // Définir le format attendu de la date (exemple : "yyyy-MM-dd")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Convertir le String en LocalDate
        LocalDate dateEmbauche = LocalDate.parse(dateEmbaucheStr, formatter);

        // Calculer l'ancienneté en années
        LocalDate today = LocalDate.now();
        return ChronoUnit.YEARS.between(dateEmbauche, today);
    }

}
