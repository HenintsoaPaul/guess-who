package itu.crypto.repository;

import itu.crypto.entity.wallet.TypeMvWallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeMvWalletRepository extends JpaRepository<TypeMvWallet, Integer> {
}
