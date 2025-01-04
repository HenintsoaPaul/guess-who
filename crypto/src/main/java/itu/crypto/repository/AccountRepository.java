package itu.crypto.repository;

import itu.crypto.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {

    // Méthode pour trouver un compte par matricule (idEmploye) et mot de passe
    Optional<Account> findByEmploye_IdEmployeAndPassword(String id_employe, String password);
}
