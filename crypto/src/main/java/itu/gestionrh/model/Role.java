package itu.gestionrh.model;

import jakarta.persistence.*;

@Entity
@Table(name = "role") // Ensure this matches your database table name
public class Role {

    @Id
    @Column(name = "id_role")  // Map the id_role column in the database to the idRole field
    private String idRole; // L'identifiant unique du rôle

    @Column(name = "title")  // Map the title column in the database to the title field
    private String title;  // Le titre du rôle (par exemple : "RH", "Employé")

    @Column(name = "desce")  // Map the desce column in the database to the desce field
    private String desce;  // La description du rôle

    @ManyToOne
    @JoinColumn(name = "id_classification", referencedColumnName = "id_classification")  // Map the foreign key
    private Classification classification;  // Association avec l'entité Classification

    // Getters and setters

    public String getIdRole() {
        return idRole;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesce() {
        return desce;
    }

    public void setDesce(String desce) {
        this.desce = desce;
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }
}
