package itu.crypto.repository.transaction.wallet;

import itu.crypto.entity.account.Account;
import itu.crypto.entity.wallet.Wallet;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    List<Wallet> findAllByAccount(Account account);

    @Modifying
    @Query("UPDATE Wallet w SET w.quantity = w.quantity + :quantity WHERE w.account.id = :accountId AND w.crypto.id = :cryptoId")
    void addCryptoToWallet(@Param("accountId") Integer accountId, @Param("cryptoId") Integer cryptoId, @Param("quantity") Integer quantity);

    @Query(value = """
                select w from Wallet w
                            where w.crypto.id = :idCrypto and w.account.id = :idAccount
            """)
    Optional<Wallet> findByCryptoAndAccount(Integer idCrypto, Integer idAccount);
}
