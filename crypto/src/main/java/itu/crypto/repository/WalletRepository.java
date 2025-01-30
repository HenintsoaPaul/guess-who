package itu.crypto.repository;

import itu.crypto.entity.Account;
import itu.crypto.entity.Wallet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    List<Wallet> findAllByAccount(Account account);

    //    Wallet findWallet(Account account , Crypto crypto);

    @Query(value = "SELECT a.id_account, a.pseudo, a.fund, c.name, w.quantity " +
               "FROM account a " +
               "JOIN wallet w ON w.id_account = a.id_account " +
               "JOIN crypto c ON c.id_crypto = w.id_crypto " +
               "WHERE w.id_account = :userId", 
       nativeQuery = true)
    List<Object[]> findWalletByUser(@Param("userId") Integer id);



}
