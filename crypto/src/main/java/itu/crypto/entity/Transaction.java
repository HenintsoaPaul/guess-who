package itu.crypto.entity;

import itu.crypto.entity.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction", nullable = false)
    private Integer id;

    @Column(name = "trans_date", nullable = false)
    private LocalDate transDate;

    @Column(name = "trans_price", nullable = false)
    private double transPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_account_receiver")
    private Account idAccountReceiver;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_account_source", nullable = false)
    private Account idAccountSource;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_transaction_type", nullable = false)
    private TransactionType idTransactionType;

}