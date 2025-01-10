package itu.crypto.repository;

import itu.crypto.entity.Account;
import itu.crypto.entity.Wallet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    List<Wallet> findAllByAccount(Account account);

    //    Wallet findWallet(Account account , Crypto crypto);

    @Query(value = "SELECT " +
        "a.pseudo, " +
        "COALESCE(SUM(p.quantity), 0) AS totalAchat, " +
        "COALESCE(SUM(sd.quantity), 0) AS totalVente, " +
        "a.fund " +
    "FROM account a " +
    "LEFT JOIN purchase p ON p.id_account = a.id_account " +
    "LEFT JOIN sale s ON s.id_account = a.id_account " +
    "LEFT JOIN sale_detail sd ON sd.id_sale = s.id_sale " +
    "GROUP BY a.pseudo, a.fund",
    nativeQuery = true)
    List<Object[]> findHistoriqueAllUser();


}
