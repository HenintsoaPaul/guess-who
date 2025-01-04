package itu.crypto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import itu.crypto.model.TypeLeave;

@Repository
public interface TypeLeaveRepository extends JpaRepository<TypeLeave, String> {
    List<TypeLeave> findAll();
}
