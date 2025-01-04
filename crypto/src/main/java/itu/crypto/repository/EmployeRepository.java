package itu.crypto.repository;

import itu.crypto.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeRepository extends JpaRepository<Employe, String> {
    @Query("SELECT e FROM Employe e WHERE e.idEmploye = :idEmploye")
    Employe employeId(@Param("idEmploye") String idEmploye);

}

