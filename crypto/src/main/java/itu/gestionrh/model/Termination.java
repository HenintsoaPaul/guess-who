package itu.gestionrh.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "termination")
public class Termination {

    @Id
    @Column(name = "id_termination")
    private String idTermination;

    @Column(name = "termination_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date terminationDate;

    @Column(name = "notice_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date noticeDate;

    @ManyToOne
    @JoinColumn(name = "id_employe", nullable = false)
    private Employe employe;

    @ManyToOne
    @JoinColumn(name = "id_contract", nullable = false)
    private Contract contract;

    @ManyToOne
    @JoinColumn(name = "id_termination_type", nullable = false)
    private TerminationType terminationType;

    // Getters et Setters

    public String getIdTermination() {
        return idTermination;
    }

    public void setIdTermination(String idTermination) {
        this.idTermination = idTermination;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public Date getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(Date noticeDate) {
        this.noticeDate = noticeDate;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public TerminationType getTerminationType() {
        return terminationType;
    }

    public void setTerminationType(TerminationType terminationType) {
        this.terminationType = terminationType;
    }
}
