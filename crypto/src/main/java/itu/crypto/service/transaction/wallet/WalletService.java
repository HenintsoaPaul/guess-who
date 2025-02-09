package itu.crypto.service.transaction.wallet;

import itu.crypto.entity.wallet.Wallet;
import itu.crypto.firebase.firestore.generalisation.BaseService;
import itu.crypto.repository.transaction.wallet.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletService implements BaseService<Wallet> {

    private final WalletRepository walletRepository;

    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }

    @Override
    public Optional<Wallet> findById(int id) {
        return walletRepository.findById(id);
    }

    public Optional<Wallet> findByCryptoAndAccount(Integer idCrypto, Integer idAccount) {
        return walletRepository.findByCryptoAndAccount(idCrypto, idAccount);
    }
}
