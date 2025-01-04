package itu.crypto.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Prime")
public class Prime {

    @Id
    @Column(name = "id_prime")
    private String idPrime;

    @Column(name = "title")
    private String title;

    @Column(name = "date_prime", nullable = false)
    private LocalDate datePrime;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "id_employe", referencedColumnName = "id_employe", nullable = false)
    private Employe employe;

    // Getters and Setters
    public String getIdPrime() {
        return idPrime;
    }

    public void setIdPrime(String idPrime) {
        this.idPrime = idPrime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDatePrime() {
        return datePrime;
    }

    public void setDatePrime(LocalDate datePrime) {
        this.datePrime = datePrime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }
}
