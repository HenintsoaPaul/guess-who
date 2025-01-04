package itu.crypto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import itu.crypto.model.*;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrimeRepository extends JpaRepository<Prime, String> {

    // Méthodes supplémentaires selon vos besoins
    List<Prime> findByEmployeIdEmploye(String employeId);

    List<Prime> findByDatePrimeBetween(LocalDate startDate, LocalDate endDate);
    
    // @Query(value="",nativeQuery=true)
    // List<Prime> employe(@Param("start_date") LocalDate startDate, @Param("end_date") LocalDate endDate,);
}

