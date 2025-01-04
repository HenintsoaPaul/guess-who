package itu.crypto.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Quarter")
public class Quarter {

    @Id
    @Column(name = "id_quarter")  // Colonne dans la base de données
    private String idQuarter;
    
    @Column(name = "name")  // Nom du trimestre
    private String name;

    // Getters et Setters
    public String getIdQuarter() {
        return idQuarter;
    }

    public void setIdQuarter(String idQuarter) {
        this.idQuarter = idQuarter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
