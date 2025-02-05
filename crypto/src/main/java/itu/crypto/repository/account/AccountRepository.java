package itu.crypto.repository.account;

import itu.crypto.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findAccountByEmail(String email);
}
