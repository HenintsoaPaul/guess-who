package itu.gestionrh.repository;

import itu.gestionrh.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, String> {
    @Query("SELECT c FROM Contract c WHERE c.employe.idEmploye = :idEmploye")
    Contract contractEmploye(@Param("idEmploye") String idEmploye);

    @Query(value = "SELECT * FROM contract WHERE id_contract NOT IN (SELECT id_contract FROM termination) AND (end_date IS NULL OR end_date >= CURRENT_DATE)", nativeQuery = true)
    List<Contract> valideContract();

    @Query(value= "SELECT c FROM Contract c WHERE c.idContract = :id_contract" )
    Contract getContractIdContract(@Param("id_contract") String idContract);
}
