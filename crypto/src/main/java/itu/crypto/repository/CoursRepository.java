package itu.crypto.repository;

import itu.crypto.entity.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CoursRepository extends JpaRepository<Cours, Integer> {
    @Query("SELECT c FROM Cours c WHERE c.id IN (SELECT MAX(c2.id) FROM Cours c2 GROUP BY c2.crypto)")
    List<Cours> findLatestCoursForEachCrypto();
}
