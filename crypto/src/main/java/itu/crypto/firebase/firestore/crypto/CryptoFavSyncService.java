package itu.crypto.firebase.firestore.crypto;

import com.google.cloud.firestore.Firestore;
import itu.crypto.entity.crypto.CryptoFav;
import itu.crypto.firebase.firestore.generalisation.FirestoreSyncService;
import itu.crypto.service.crypto.CryptoFavService;
import org.springframework.stereotype.Service;

@Service
public class CryptoFavSyncService extends FirestoreSyncService<CryptoFav, CryptoFavDocument> {

    public CryptoFavSyncService(Firestore firestore, CryptoFavService cryptoFavService) {
        super(firestore, cryptoFavService, "crypto_fav");
    }

    @Override
    protected CryptoFavDocument toDocument(CryptoFav entity) {
        return new CryptoFavDocument(entity);
    }

    @Override
    protected CryptoFav toEntity(CryptoFavDocument document) {
        return document.toEntity();
    }

    @Override
    protected String getEntityId(CryptoFav entity) {
        return entity.getId().toString();
    }

    @Override
    protected Class<CryptoFavDocument> getDocumentClass() {
        return CryptoFavDocument.class;
    }
}
