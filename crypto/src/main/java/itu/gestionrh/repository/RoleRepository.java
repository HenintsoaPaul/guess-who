package itu.gestionrh.repository;

import itu.gestionrh.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
    
}