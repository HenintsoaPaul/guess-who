package itu.crypto.repository.account;

import itu.crypto.entity.account.Account;
import itu.crypto.entity.account.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Integer> {
    List<FcmToken> findAllByAccount(Account account);
}
