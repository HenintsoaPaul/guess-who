package itu.crypto.repository.transaction.wallet;

import itu.crypto.entity.wallet.MvWallet;
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

    @Query(value = """
                select mw from MvWallet mw where mw.wallet.crypto.id = :idCrypto
            """)
    List<MvWallet> findAllByIdCrypto(Integer idCrypto);

    @Query(value = """
                select mw from MvWallet mw where mw.wallet.account.id = :idAccount
            """)
    List<MvWallet> findAllByIdAccount(Integer idAccount);
}
