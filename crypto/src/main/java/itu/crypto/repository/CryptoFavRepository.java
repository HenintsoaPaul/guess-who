package itu.crypto.repository;

import itu.crypto.entity.crypto.CryptoFav;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoFavRepository extends JpaRepository<CryptoFav, Integer> {
}
