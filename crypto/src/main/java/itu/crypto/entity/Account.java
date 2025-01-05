package itu.crypto.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "id_account", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String idAccount;

    @Column(name = "pseudo", nullable = false, length = 250)
    private String pseudo;

    @Column(name = "email", nullable = false, length = 250)
    private String email;

    @Column(name = "fund", nullable = false)
    private double fund;

}
