package itu.gestionrh.service;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import itu.gestionrh.model.*;
import itu.gestionrh.repository.ContractRepository;
import itu.gestionrh.controller.*;

public class PayrollService {

    private ContractRepository contractRepository;

    // Calcul de la fiche de paie complète
    // public List<PayrollDetails> calculatePayroll(String employeId, String dateDebut, String dateFin) {
    //     List<PayrollDetails> details = new ArrayList<>();
        
    //     // Exemple d'employé, à récupérer de la base de données ou d'un service
    //     Contract contract = contractRepository.contractEmploye(employeId); 

    //     // Calcul du salaire de base (exemple fictif)
    //     double salaireDeBase = contract.getSalary().doubleValue();
    //     details.add(new PayrollDetails("Salaire de base", salaireDeBase));

    //     // Calcul des congés pris
    //     double congésPris = calculateCongesPris(employe, dateDebut, dateFin);
    //     details.add(new PayrollDetails("Congés pris", -congésPris)); // Congés en retrait

    //     // Calcul des primes 
    //     double primeRendement = calculatePrimeRendement(employe, dateDebut, dateFin);
    //     details.add(new PayrollDetails("Prime de rendement", primeRendement));

    //     // Calcul des retenues (par exemple, CNAPS et IRSA)
    //     double retenueCnaps = calculateRetenueCnaps(salaireDeBase);
    //     details.add(new PayrollDetails("Retenue CNAPS", -retenueCnaps));  // Retenue négative

    //     double retenueIrsa = calculateRetenueIrsa(salaireDeBase);
    //     details.add(new PayrollDetails("Retenue IRSA", -retenueIrsa));  // Retenue négative

    //     // Calcul du salaire brut
    //     double salaireBrut = salaireDeBase + primeRendement - congésPris;
    //     details.add(new PayrollDetails("Salaire Brut", salaireBrut));

    //     // Calcul du net à payer
    //     double totalRetenues = retenueCnaps + retenueIrsa;
    //     double netAPayer = salaireBrut - totalRetenues;
    //     details.add(new PayrollDetails("Net à payer", netAPayer));

    //     return details;
    // }

    // Exemple de calcul des congés pris (à adapter selon les règles de votre entreprise)
    private double calculateCongesPris(Employe employe, String dateDebut, String dateFin) {
        // Exemple de calcul basé sur les dates fournies (dateDebut, dateFin) et la politique de congés
        // Retourne un montant à déduire pour les congés pris.
        return 100.0;  // Valeur fictive pour l'exemple
    }

    // Exemple de calcul de la prime de rendement (à adapter selon la politique de l'entreprise)
    private double calculatePrimeRendement(Employe employe, String dateDebut, String dateFin) {
        // Exemple de calcul pour une prime de rendement (peut être basé sur des objectifs)
        return 200.0;  // Valeur fictive pour l'exemple
    }

    // Calcul de la retenue CNAPS
    private double calculateRetenueCnaps(double salaireDeBase) {
        return salaireDeBase * 0.06;  // Exemple de taux de retenue CNAPS de 6%
    }

    // Calcul de la retenue IRSA
    private double calculateRetenueIrsa(double salaireDeBase) {
        return salaireDeBase * 0.05;  // Exemple de taux de retenue IRSA de 5%
    }

    
}
