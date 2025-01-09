package itu.crypto.repository;

import itu.crypto.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface CryptoRepository extends JpaRepository<Crypto, Integer> {
    Optional<Crypto> findBySymbol(String symbol);
}
