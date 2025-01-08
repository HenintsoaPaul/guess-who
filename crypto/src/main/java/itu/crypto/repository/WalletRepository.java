package itu.crypto.repository;


import itu.crypto.entity.Account;
import itu.crypto.entity.CryptoCurrency;
import itu.crypto.entity.Wallet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    List<Wallet> findByAccount(Account account);
    Wallet findWallet(Account account , CryptoCurrency cryptoCurrency);
}
