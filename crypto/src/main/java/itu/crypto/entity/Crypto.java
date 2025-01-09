package itu.crypto.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "crypto")
public class Crypto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_crypto", nullable = false)
    private Long idCrypto;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "symbol", nullable = false)
    private String symbol;

    public Crypto() {}

    public Crypto(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public Long getId() {
        return idCrypto;
    }

    public void setId(Long id) {
        this.idCrypto = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}