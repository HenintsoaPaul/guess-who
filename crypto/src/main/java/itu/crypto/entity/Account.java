package itu.crypto.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "pseudo")
    private String pseudo;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private double fund;

    public Account() {}

    public Account(String email, String pseudo, String password, double fund) {
        this.email = email;
        this.pseudo = pseudo;
        this.password = password;
        this.fund = fund;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getFund() {
        return fund;
    }

    public void setFund(double fund) {
        this.fund = fund;
    }

}
