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
        "p.name, "+
        "ca.rubrique, " +
        "d.name " +
    "FROM Product p " +
    "JOIN age_product ap ON p.id_product = ap.id_product " +
    "JOIN categorie_age ca ON ap.id_categorie_age  = ca.id_categorie_age " +
    "JOIN disease_product dp ON p.id_product = dp.id_product " +
    "JOIN disease d ON dp.id_disease = d.id_disease",
    nativeQuery = true)
    List<Object[]> findAllProductDetails();

}
