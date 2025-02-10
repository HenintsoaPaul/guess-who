package itu.crypto.repository.transaction;

import itu.crypto.entity.Sale;
import itu.crypto.entity.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, Integer> {
    List<SaleDetail> findAllBySale(Sale sale);

    @Query("SELECT s FROM SaleDetail s WHERE s.quantityLeft > 0")
    List<SaleDetail> findAllDispo();

    @Query("SELECT s FROM SaleDetail s WHERE s.id = :id")
    SaleDetail findAllById(int id);
}
