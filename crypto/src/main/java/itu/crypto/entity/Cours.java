package itu.crypto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@ToString
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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_crypto", nullable = false)
    private Crypto crypto;

    public Cours(double pu, LocalDateTime dateCours, Crypto crypto) {
        this.setPu(pu);
        this.setDateCours(dateCours);
        this.setCrypto(crypto);
    }

    public Cours(Integer id, double pu, LocalDateTime dateCours, Crypto crypto) {
        this.setId(id);
        this.setPu(pu);
        this.setDateCours(dateCours);
        this.setCrypto(crypto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cours cours = (Cours) o;
        return Double.compare(cours.pu, pu) == 0 &&
                Objects.equals(dateCours, cours.dateCours) &&
                Objects.equals(crypto, cours.crypto);
    }
}