package itu.crypto.repository;

import itu.crypto.entity.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.*;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {
    @Query(value = "SELECT c FROM Cours c WHERE c.id_crypto = :idCrypto ORDER BY c.date_cours DESC",
    nativeQuery = true)
    Optional<Cours> findLatestCoursByIdCrypto(@Param("idCrypto") Long idCrypto);
}

