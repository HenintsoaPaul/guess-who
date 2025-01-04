package itu.crypto.model;

import jakarta.persistence.*;

@Entity
@Table(name = "terminationtype")
public class TerminationType {

    @Id
    @Column(name = "id_termination_type")
    private String idTerminationType;

    @Column(name = "notice_duration", nullable = false)
    private Integer noticeDuration = 0;

    @Column(name = "desce", nullable = false)
    private String description;

    // Getters et Setters

    public String getIdTerminationType() {
        return idTerminationType;
    }

    public void setIdTerminationType(String idTerminationType) {
        this.idTerminationType = idTerminationType;
    }

    public Integer getNoticeDuration() {
        return noticeDuration;
    }

    public void setNoticeDuration(Integer noticeDuration) {
        this.noticeDuration = noticeDuration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
