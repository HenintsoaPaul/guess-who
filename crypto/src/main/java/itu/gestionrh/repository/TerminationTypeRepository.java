package itu.gestionrh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import itu.gestionrh.model.TerminationType;

@Repository
public interface TerminationTypeRepository extends JpaRepository<TerminationType, String> {
    List<TerminationType> findAll();
}
