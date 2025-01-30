package itu.crypto.dto;

public class WalletDTO {
    private Integer idAccount;
    private String pseudo;
    private Double fund;
    private String cryptoName;
    private Integer quantity;

    public WalletDTO() {}

    public WalletDTO(Integer idAccount, String pseudo, Double fund, String cryptoName, Integer quantity) {
        this.idAccount = idAccount;
        this.pseudo = pseudo;
        this.fund = fund;
        this.cryptoName = cryptoName;
        this.quantity = quantity;
    }

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
}
