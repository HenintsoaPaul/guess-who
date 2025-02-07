package itu.crypto.firebase.firestore.crypto;

import com.google.cloud.firestore.Firestore;
import itu.crypto.entity.crypto.CryptoFav;
import itu.crypto.firebase.firestore.FirestoreChangeListener;
import itu.crypto.service.crypto.CryptoFavService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CryptoFavChangeListener extends FirestoreChangeListener<CryptoFav, CryptoFavDocument> {

    private final CryptoFavService cryptoFavService;

    public CryptoFavChangeListener(Firestore firestore, CryptoFavService cryptoFavService) {
        super(firestore, cryptoFavService, "crypto_fav");
        this.cryptoFavService = cryptoFavService;
    }

    @Override
    protected CryptoFav toEntity(CryptoFavDocument document) {
        return document.toEntity();
    }

    @Override
    protected Class<CryptoFavDocument> getDocumentClass() {
        return CryptoFavDocument.class;
    }

    @Override
    protected void updateDatabase(CryptoFav entity) {
        cryptoFavService.updateOrCreate(entity); // 🔥 Mise à jour ou insertion
    }

    @Override
    protected void deleteFromDatabase(String entityId) {
        cryptoFavService.deleteById(Integer.parseInt(entityId)); // 🔥 Suppression de la ligne
    }
}
