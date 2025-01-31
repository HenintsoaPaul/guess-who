package itu.crypto.repository;

import itu.crypto.entity.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CoursRepository extends JpaRepository<Cours, Integer> {
    List<Cours> findById(int idCrypto);

    @Query(value = """
            select tab1.*
            from cours tab1
                     join (select c.id_crypto, max(pu) as pp from cours c group by c.id_crypto) tab2
                         on tab1.id_crypto = tab2.id_crypto and tab1.pu = tab2.pp
            """, nativeQuery = true)
    List<Cours> findMaxCoursForeachCrypto(List<Cours> cours);

    @Query(value = """
            select tab1.*
            from cours tab1
                     join (select c.id_crypto, min(pu) as pp from cours c group by c.id_crypto) tab2
                         on tab1.id_crypto = tab2.id_crypto and tab1.pu = tab2.pp
            """, nativeQuery = true)
    List<Cours> findMinCoursForeachCrypto(List<Cours> cours);
}
