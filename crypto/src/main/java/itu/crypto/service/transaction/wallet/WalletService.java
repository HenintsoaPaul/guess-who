package itu.crypto.service.transaction.wallet;

import itu.crypto.entity.wallet.Wallet;
import itu.crypto.firebase.firestore.generalisation.BaseService;
import itu.crypto.repository.transaction.wallet.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService implements BaseService<Wallet> {

    private final WalletRepository walletRepository;

    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }
}
