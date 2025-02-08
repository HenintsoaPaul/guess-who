package itu.crypto.firebase.firestore.crypto;

import com.google.cloud.firestore.Firestore;
import itu.crypto.entity.crypto.Crypto;
import itu.crypto.firebase.firestore.generalisation.FirestoreSyncService;
import itu.crypto.service.crypto.CryptoService;
import org.springframework.stereotype.Service;

@Service
public class CryptoSyncService extends FirestoreSyncService<Crypto, CryptoDocument> {

    public CryptoSyncService(Firestore firestore, CryptoService cryptoService) {
        super(firestore, cryptoService, "crypto");
    }

    @Override
    protected CryptoDocument toDocument(Crypto entity) {
        return new CryptoDocument(entity);
    }

    @Override
    protected Crypto toEntity(CryptoDocument document) {
        return document.toEntity();
    }

    @Override
    protected String getEntityId(Crypto entity) {
        return entity.getId().toString();
    }

    @Override
    protected Class<CryptoDocument> getDocumentClass() {
        return CryptoDocument.class;
    }
}
