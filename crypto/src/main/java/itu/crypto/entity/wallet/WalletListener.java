package itu.crypto.entity.wallet;

import itu.crypto.firebase.firestore.wallet.WalletSyncService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class WalletListener {

    private final WalletSyncService walletSyncService;

    @Autowired
    public WalletListener(@Lazy WalletSyncService walletSyncService) {
        this.walletSyncService = walletSyncService;
    }

    @PostPersist
    public void apresSauvegarde(Wallet wallet) {
        walletSyncService.saveAsDocument(wallet);
    }

    @PostUpdate
    public void apresModification(Wallet wallet) {
        walletSyncService.updateAsDocument(wallet);
    }
}
