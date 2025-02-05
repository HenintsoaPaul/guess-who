package itu.crypto.entity;

import itu.crypto.entity.account.Account;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@ToString
@NoArgsConstructor
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

    public boolean isDepotRetrait() {
        return this.getTypeMvFund().getId() == 1 || this.getTypeMvFund().getId() == 2;
    }
}