package itu.crypto.entity;

import jakarta.persistence.*;
import java.sql.*;

@Entity
@Table(name = "mv_fund")
public class MvFund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_mv")
    private Date date;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_mv_fund", nullable = false)
    private TypeMvFund typeMvFund;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_account", nullable = false)
    private Account account;

    public MvFund() {}

    public MvFund(Date date, Integer quantity, TypeMvFund typeMvFund, Account account) {
        this.date = date;
        this.quantity = quantity;
        this.typeMvFund = typeMvFund;
        this.account = account;
    }


}