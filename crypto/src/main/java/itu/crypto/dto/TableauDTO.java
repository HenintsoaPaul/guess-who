package itu.crypto.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class TableauDTO {
    private String user;
    private double total_achat;
    private double total_vente;
    private double valeur_portefeuil;

    public TableauDTO(String user, double total_achat, double total_vente, double valeur_portefeuil) {
        this.user = user;
        this.total_achat = total_achat;
        this.total_vente = total_vente;
        this.valeur_portefeuil = valeur_portefeuil;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public double getTotal_achat() {
        return total_achat;
    }

    public void setTotal_achat(double total_achat) {
        this.total_achat = total_achat;
    }

    public double getTotal_vente() {
        return total_vente;
    }

    public void setTotal_vente(double total_vente) {
        this.total_vente = total_vente;
    }

    public double getValeur_portefeuil() {
        return valeur_portefeuil;
    }

    public void setValeur_portefeuil(double valeur_portefeuil) {
        this.valeur_portefeuil = valeur_portefeuil;
    }

}
