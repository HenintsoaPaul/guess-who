package itu.crypto.repository.account;

import itu.crypto.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findAccountByEmail(String email);

    @Query(value = """
            select a.* from Account a
            where a.id_account in
                              (select distinct (cf.id_account)
                                           from crypto_fav cf
                                                       where cf.id_crypto = :idCrypto and cf.on_fav = true)
            """, nativeQuery = true)
    List<Account> findAllToNotifyOnCryptoPurchase(int idCrypto);
}
