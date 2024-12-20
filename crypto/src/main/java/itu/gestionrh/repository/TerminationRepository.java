package itu.gestionrh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import itu.gestionrh.model.Termination;

@Repository
public interface TerminationRepository extends JpaRepository<Termination, String> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO Termination (notice_date, termination_date, id_contract, id_employe, id_termination_type) VALUES (:notice_date, :termination_date, :id_contract, :id_employe, :id_termination_type)", nativeQuery = true)
    void insertValue(@Param("notice_date") LocalDate noticeDate,
            @Param("termination_date") LocalDate terminationDate,
            @Param("id_contract") String id_contract,
            @Param("id_employe") String id_employe,
            @Param("id_termination_type") String id_termination_type);

}
