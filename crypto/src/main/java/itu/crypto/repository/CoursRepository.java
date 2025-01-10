package itu.crypto.repository;

import itu.crypto.entity.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CoursRepository extends JpaRepository<Cours, Integer> {
    List<Cours> findById(int idCrypto);
}
