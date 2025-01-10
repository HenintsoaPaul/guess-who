package itu.crypto.repository;

import itu.crypto.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Integer> {
    @Query("SELECT s FROM Sale s WHERE s.account.idAccount = :idAccount")
    List<Sale> findAllByIdAccount(Integer idAccount);

    @Query(value = "SELECT " +
        "crp.name, " +
        "sld.quantity_left " +
    "FROM sale sl " +
    "JOIN sale_detail sld ON sl.id_sale = sld.id_sale " +
    "JOIN crypto crp ON crp.id_crypto = sld.id_crypto " +
    "WHERE sld.quantity_left > 0", 
    nativeQuery = true)
    List<Object[]> findAchatDispo();

}
