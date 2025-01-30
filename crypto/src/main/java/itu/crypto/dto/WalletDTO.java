package itu.crypto.dto;

import java.sql.Date;

public class WalletDTO {
    private Integer idAccount;
    private String pseudo;
    private Double fund;
    private String cryptoName;
    private Integer quantity;
    private Date purchaseDate;
    private Double totalPrice;

    // Constructeur par défaut
    public WalletDTO() {}

    // Constructeur avec tous les paramètres
    public WalletDTO(Integer idAccount, String pseudo, Double fund, String cryptoName, Integer quantity,
        Date purchaseDate, Double totalPrice) {
        this.idAccount = idAccount;
        this.pseudo = pseudo;
        this.fund = fund;
        this.cryptoName = cryptoName;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
        this.totalPrice = totalPrice;
    }

    // Getters et setters pour chaque champ

    public Integer getIdAccount() { return idAccount; }
    public void setIdAccount(Integer idAccount) { this.idAccount = idAccount; }

    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }

    public Double getFund() { return fund; }
    public void setFund(Double fund) { this.fund = fund; }

    public String getCryptoName() { return cryptoName; }
    public void setCryptoName(String cryptoName) { this.cryptoName = cryptoName; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Date getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(Date purchaseDate) { this.purchaseDate = purchaseDate; }

    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
}
