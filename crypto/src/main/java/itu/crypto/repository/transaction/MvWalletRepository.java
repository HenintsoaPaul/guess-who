package itu.crypto.repository.transaction;

import itu.crypto.entity.MvWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MvWalletRepository extends JpaRepository<MvWallet, Integer> {
}
