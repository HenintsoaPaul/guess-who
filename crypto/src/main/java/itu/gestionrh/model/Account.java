package itu.gestionrh.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Account")
public class Account {

    @Id
    @Column(name = "id_account")  // The column name in the database
    private String idAccount;
    
    @Column(name = "password")  // The column name in the database
    private String password;
    
    @ManyToOne
    @JoinColumn(name = "id_employe")  // The foreign key column name in the database
    private Employe employe;

    // Getters et Setters
    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe Employe) {
        this.employe = Employe;
    }
}
