package itu.crypto.repository;

import itu.crypto.entity.crypto.CryptoFav;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CryptoFavRepository extends JpaRepository<CryptoFav, Integer> {
    @Query(value = """
            select fav from CryptoFav fav
                where fav.crypto.id = :idCrypto and fav.account.id = :idAccount
            """)
    Optional<CryptoFav> findByCryptoAndAccount(int idCrypto, int idAccount);
}
