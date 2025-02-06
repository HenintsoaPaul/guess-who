package itu.crypto.entity.fund;

import itu.crypto.entity.TypeMvFund;
import itu.crypto.entity.account.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "pending_mv_fund")
public class PendingMvFund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pending_mv_fund", nullable = false)
    private Integer id;

    @Column(name = "date_pending", nullable = false)
    private LocalDateTime datePending;

    @Column(name = "date_validation")
    private LocalDateTime dateValidation;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private Double amount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pending_state", nullable = false)
    private PendingState pendingState;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_type_mv_fund", nullable = false)
    private TypeMvFund typeMvFund;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_account", nullable = false)
    private Account account;

    @OneToMany(mappedBy = "pendingMvFund")
    private Set<MvFund> mvFunds = new LinkedHashSet<>();

}