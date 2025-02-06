package itu.crypto.entity.crypto;

import itu.crypto.firebase.firestore.fav.CryptoFavSyncService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class CryptoFavListener {

    private final CryptoFavSyncService cryptoFavSyncService;

    @Autowired
    public CryptoFavListener(@Lazy CryptoFavSyncService cryptoFavSyncService) {
        this.cryptoFavSyncService = cryptoFavSyncService;
    }

    @PostPersist
    public void apresSauvegarde(CryptoFav cryptoFav) {
        cryptoFavSyncService.saveAsDocument(cryptoFav);
    }

    @PostUpdate
    public void apresModification(CryptoFav cryptoFav) {
        cryptoFavSyncService.updateAsDocument(cryptoFav);
    }
}
