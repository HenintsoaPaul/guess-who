package itu.crypto.firebase.firestore.purchase;

import com.google.cloud.firestore.Firestore;
import itu.crypto.entity.purchase.Purchase;
import itu.crypto.firebase.firestore.generalisation.FirestoreSyncService;
import itu.crypto.service.transaction.PurchaseService;
import org.springframework.stereotype.Service;

@Service
public class PurchaseSyncService extends FirestoreSyncService<Purchase, PurchaseDocument> {

    public PurchaseSyncService(Firestore firestore, PurchaseService purchaseService) {
        super(firestore, purchaseService, "purchase");
    }

    @Override
    protected PurchaseDocument toDocument(Purchase entity) {
        return new PurchaseDocument(entity);
    }

    @Override
    protected Purchase toEntity(PurchaseDocument document) {
        return document.toEntity();
    }

    @Override
    protected String getEntityId(Purchase entity) {
        return entity.getId().toString();
    }

    @Override
    protected Class<PurchaseDocument> getDocumentClass() {
        return PurchaseDocument.class;
    }
}
