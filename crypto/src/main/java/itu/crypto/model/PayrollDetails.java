package itu.crypto.model;

public class PayrollDetails {
    private String description;
    private double montant;

    // Constructeur
    public PayrollDetails(String description, double montant) {
        this.description = description;
        this.montant = montant;
    }

    // Getters et Setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}
