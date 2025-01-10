package itu.crypto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "cours")
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cours", nullable = false)
    private Integer id;

    @Column(name = "pu", nullable = false)
    private double pu;

    @Column(name = "date_cours", nullable = false)
    private LocalDate dateCours;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_crypto", nullable = false)
    private Crypto crypto;

}