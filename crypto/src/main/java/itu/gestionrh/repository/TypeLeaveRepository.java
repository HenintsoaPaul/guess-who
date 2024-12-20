package itu.gestionrh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import itu.gestionrh.model.TypeLeave;

@Repository
public interface TypeLeaveRepository extends JpaRepository<TypeLeave, String> {
    List<TypeLeave> findAll();
}
