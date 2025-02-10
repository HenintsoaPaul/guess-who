package itu.crypto.repository.account;

import itu.crypto.entity.account.Account;
import itu.crypto.entity.account.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    List<Admin> findByAccount(Account account);
}
