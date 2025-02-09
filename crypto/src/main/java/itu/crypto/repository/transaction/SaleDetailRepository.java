package itu.crypto.repository.transaction;

import itu.crypto.entity.sale.Sale;
import itu.crypto.entity.sale.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, Integer> {
    List<SaleDetail> findAllBySale(Sale sale);
}
