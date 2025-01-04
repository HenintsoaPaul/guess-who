package itu.crypto.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class LeaveRequest {
    @Id
    private String idLeaveRequest;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "id_employe")
    private Employe employe;

    // Getters and Setters
    public String getIdLeaveRequest() { return idLeaveRequest; }
    public void setIdLeaveRequest(String idLeaveRequest) { this.idLeaveRequest = idLeaveRequest; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { this.employe = employe; }
}
