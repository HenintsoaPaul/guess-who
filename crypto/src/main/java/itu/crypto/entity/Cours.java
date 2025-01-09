package itu.crypto.entity;

import jakarta.persistence.*;
import java.sql.*;

@Entity
@Table(name = "cours")
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cours", nullable = false)
    private Long id;

    @Column(name = "date_cours", nullable = false)
    private Date date;

    @Column(name = "pu", nullable = false)
    private double pu;

    @ManyToOne
    @JoinColumn(name = "id_crypto", nullable = false)
    private Crypto crypto;


    public Cours() {}

    public Cours(Date date, double pu, Crypto crypto) {
        this.date = date;
        this.pu = pu;
        this.crypto = crypto;
    }

    public double getPu() {
        return pu;
    }

}