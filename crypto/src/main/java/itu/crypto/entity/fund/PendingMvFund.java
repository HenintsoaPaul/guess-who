package itu.crypto.entity.fund;

import itu.crypto.entity.account.Account;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
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

    @Column(name = "amount", nullable = false)
    private Double amount;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_pending_state", nullable = false)
    private PendingState pendingState;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_type_mv_fund", nullable = false)
    private TypeMvFund typeMvFund;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_account", nullable = false)
    private Account account;

    @OneToMany(mappedBy = "pendingMvFund")
    private Set<MvFund> mvFunds = new LinkedHashSet<>();

}