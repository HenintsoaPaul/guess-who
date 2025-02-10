package itu.crypto.firebase.firestore.crypto;

import com.google.cloud.firestore.Firestore;
import itu.crypto.entity.crypto.CryptoFav;
import itu.crypto.firebase.firestore.generalisation.FirestoreChangeListener;
import itu.crypto.service.DateService;
import itu.crypto.service.crypto.CryptoFavService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CryptoFavChangeListener extends FirestoreChangeListener<CryptoFav, CryptoFavMobDocument> {

    private final CryptoFavService cryptoFavService;
    private final DateService dateService;

    public CryptoFavChangeListener(Firestore firestore, CryptoFavService cryptoFavService, DateService dateService) {
        super(firestore, cryptoFavService, "crypto_fav");
        this.cryptoFavService = cryptoFavService;
        this.dateService = dateService;
    }

    @Override
    protected CryptoFav toEntity(CryptoFavMobDocument document) {
        CryptoFav entity = document.toEntity();

        entity.setDateCryptoFav(dateService.strToLocalDateTime(document.getDateCryptoFav()));
        entity.setCrypto(cryptoFavService.findCryptoById(document.getCrypto().getId()));
        entity.setAccount(cryptoFavService.findAccountById(document.getAccount().getId()));

        return entity;
    }

    @Override
    protected Class<CryptoFavMobDocument> getDocumentClass() {
        return CryptoFavMobDocument.class;
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
