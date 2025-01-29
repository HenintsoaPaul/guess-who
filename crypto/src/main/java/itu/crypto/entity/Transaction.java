package itu.crypto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@ToString
public class Transaction {
    private Integer id;
    private LocalDate transDate;
    private double transPrice;
    private Account accountReceiver;
    private Account accountSource;
    private TransactionType transactionType;

    public Transaction(Purchase purchase) {
        this.id = purchase.getId();
//        this.transDate = purchase.getDaty();
//        this.transPrice = purchase.getQuantity
    }
}