package itu.crypto.entity;

import jakarta.persistence.*;
import java.sql.*;

@Entity
@Table(name = "cours")
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_cours", nullable = false)
    private Date date;

    @Column(name = "pu", precision = 15, scale = 2, nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "id_crypto", nullable = false)
    private Crypto crypto;


    public Cours() {}

    public Cours(Date date, double price, Crypto crypto) {
        this.date = date;
        this.price = price;
        this.crypto = crypto;
    }

}