package itu.crypto.repository;

import itu.crypto.entity.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoRepository extends JpaRepository<Crypto, Integer> {
}
