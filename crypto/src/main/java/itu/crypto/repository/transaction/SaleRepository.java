package itu.crypto.repository.transaction;

import itu.crypto.entity.Sale;
import itu.crypto.entity.SaleDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Integer> {
    @Query("SELECT s FROM Sale s WHERE s.account.id = :idAccount")
    List<Sale> findAllByIdAccount(Integer idAccount);

    
    @Query("SELECT s FROM Sale s WHERE s.id = :id")
    Sale findAllById(int id);
}
