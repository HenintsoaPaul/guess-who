package itu.crypto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import itu.crypto.model.TerminationType;

@Repository
public interface TerminationTypeRepository extends JpaRepository<TerminationType, String> {
    List<TerminationType> findAll();
}
