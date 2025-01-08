package itu.crypto.repository;

import itu.crypto.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findAccountByEmail(String email);
}
