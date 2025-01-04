package itu.crypto.repository;

import itu.crypto.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, String> {
    List<LeaveRequest> findByEmploye_IdEmploye(String idEmploye);
}
