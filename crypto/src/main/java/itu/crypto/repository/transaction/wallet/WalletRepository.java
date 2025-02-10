package itu.crypto.repository.transaction.wallet;

import itu.crypto.entity.account.Account;
import itu.crypto.entity.wallet.Wallet;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    List<Wallet> findAllByAccount(Account account);

    @Query(value = """
                select w from Wallet w
                            where w.crypto.id = :idCrypto and w.account.id = :idAccount
            """)
    Optional<Wallet> findByCryptoAndAccount(Integer idCrypto, Integer idAccount);
}
