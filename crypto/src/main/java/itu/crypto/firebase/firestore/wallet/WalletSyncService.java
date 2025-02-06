package itu.crypto.firebase.firestore.wallet;

import com.google.cloud.firestore.Firestore;
import itu.crypto.entity.wallet.Wallet;
import itu.crypto.firebase.firestore.generalisation.GenericSyncService;
import itu.crypto.service.transaction.wallet.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WalletSyncService extends GenericSyncService<Wallet, WalletDocument> {

    public WalletSyncService(Firestore firestore, WalletService walletService) {
        super(firestore, walletService, "wallet");
    }

    @Override
    protected WalletDocument toDocument(Wallet entity) {
        return new WalletDocument(entity);
    }

    @Override
    protected Wallet toEntity(WalletDocument document) {
        return document.toEntity();
    }

    @Override
    protected String getEntityId(Wallet entity) {
        return entity.getId().toString();
    }

    @Override
    protected Class<WalletDocument> getDocumentClass() {
        return WalletDocument.class;
    }
}
