package itu.crypto.repository.transaction.wallet;

import itu.crypto.entity.account.Account;
import itu.crypto.entity.wallet.Wallet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    List<Wallet> findAllByAccount(Account account);

    //    Wallet findWallet(Account account , Crypto crypto);
}
