package itu.crypto.repository.transaction;

import itu.crypto.entity.MvWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface MvWalletRepository extends JpaRepository<MvWallet, Integer> {
    @Query(value = """
            select mw from MvWallet mw where mw.dateMv >= :dateMin
            """)
    List<MvWallet> findAllAfterDateMin(LocalDate dateMin);

    @Query(value = """
            select mw from MvWallet mw where mw.dateMv <= :dateMax
            """)
    List<MvWallet> findAllBeforeDateMax(LocalDate dateMax);

    @Query(value = """
            select mw from MvWallet mw where mw.dateMv >= :dateMin and mw.dateMv <= :dateMax
            """)
    List<MvWallet> findAllInInterval(LocalDate dateMin, LocalDate dateMax);
}
