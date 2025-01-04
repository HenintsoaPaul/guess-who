package itu.crypto.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TypeLeave")
public class TypeLeave {

    @Id
    @Column(name = "id_type_leave", nullable = false)
    private String idTypeLeave;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "exceptional")
    private Boolean exceptional;

    // Getters and Setters

    public String getIdTypeLeave() {
        return idTypeLeave;
    }

    public void setIdTypeLeave(String idTypeLeave) {
        this.idTypeLeave = idTypeLeave;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getExceptional() {
        return exceptional;
    }

    public void setExceptional(Boolean exceptional) {
        this.exceptional = exceptional;
    }
}
