package itu.gestionrh.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "Employe")
public class Employe {

    @Id
    @Column(name = "id_employe")  // The column name in the database
    private String idEmploye;
    
    @Column(name = "firstname")  // The column name in the database
    private String firstName;
    
    @Column(name = "name")  // The column name in the database
    private String name;

    @Column(name = "email")  // The column name in the database
    private String email;

    // Getters et Setters
    public String getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(String idEmploye) {
        this.idEmploye = idEmploye;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String lastName) {
        this.name = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
