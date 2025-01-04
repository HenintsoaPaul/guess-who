package itu.crypto.repository;

import itu.crypto.entity.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Integer> {
}
