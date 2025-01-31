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
@Table(name = "mv_fund")
public class MvFund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mv_fund", nullable = false)
    private Integer id;

    @Column(name = "date_mv")
    private LocalDate dateMv;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "id_source")
    private Integer idSource;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_account", nullable = false)
    private Account account;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_type_mv_fund", nullable = false)
    private TypeMvFund typeMvFund;

}