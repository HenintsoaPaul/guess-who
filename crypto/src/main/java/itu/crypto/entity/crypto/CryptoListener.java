package itu.crypto.entity.crypto;

import itu.crypto.firebase.firestore.crypto.CryptoSyncService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class CryptoListener {

    private final CryptoSyncService cryptoSyncService;

    @Autowired
    public CryptoListener(@Lazy CryptoSyncService cryptoSyncService) {
        this.cryptoSyncService = cryptoSyncService;
    }

    @PostPersist
    public void apresSauvegarde(Crypto crypto) {
        cryptoSyncService.saveAsDocument(crypto);
    }

    @PostUpdate
    public void apresModification(Crypto crypto) {
        cryptoSyncService.updateAsDocument(crypto);
    }
}
