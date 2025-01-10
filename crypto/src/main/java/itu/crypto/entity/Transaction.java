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
    private Account accountReceiver;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_account_source", nullable = false)
    private Account accountSource;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_transaction_type", nullable = false)
    private TransactionType transactionType;

}