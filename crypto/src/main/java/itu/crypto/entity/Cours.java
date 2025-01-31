package itu.crypto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name = "cours")
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cours", nullable = false)
    private Integer id;

    @Column(name = "pu", nullable = false)
    private double pu;

    @Column(name = "date_cours", nullable = false)
    private LocalDateTime dateCours;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_crypto", nullable = false)
    private Crypto crypto;

    public Cours(double pu, LocalDateTime dateCours, Crypto crypto) {
        this.setPu(pu);
        this.setDateCours(dateCours);
        this.setCrypto(crypto);
    }
}