package itu.crypto.repository;

import itu.crypto.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Integer> {
    @Query("SELECT s FROM Sale s WHERE s.account.idAccount = :idAccount")
    List<Sale> findAllByIdAccount(Integer idAccount);
}
