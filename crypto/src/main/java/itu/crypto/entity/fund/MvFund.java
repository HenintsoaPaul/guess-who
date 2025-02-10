package itu.crypto.entity.fund;

import itu.crypto.entity.account.Account;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@ToString
@Table(name = "mv_fund")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(MvFundListener.class)
public class MvFund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mv_fund", nullable = false)
    private Integer id;

    @Column(name = "date_mv")
    private LocalDateTime dateMv;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "id_source")
    private Integer idSource;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_account", nullable = false)
    private Account account;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_type_mv_fund", nullable = false)
    private TypeMvFund typeMvFund;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pending_mv_fund")
    private PendingMvFund pendingMvFund;

    public MvFund(PendingMvFund pendingMvFund) {
        this.id = null;
        this.dateMv = pendingMvFund.getDateValidation();
        this.amount = pendingMvFund.getAmount();

        this.idSource = pendingMvFund.getId();

        this.account = pendingMvFund.getAccount();
        this.typeMvFund = pendingMvFund.getTypeMvFund();
        this.pendingMvFund = pendingMvFund;
    }

    public boolean isDepotRetrait() {
        return this.getTypeMvFund().getId() == 1 || this.getTypeMvFund().getId() == 2;
    }
}