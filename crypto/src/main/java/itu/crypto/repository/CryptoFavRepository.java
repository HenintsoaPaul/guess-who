package itu.crypto.repository;

import itu.crypto.entity.fav.CryptoFav;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoFavRepository extends JpaRepository<CryptoFav, Integer> {
}
