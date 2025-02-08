package itu.crypto.firebase.firestore.purchase;

import com.google.cloud.firestore.Firestore;
import itu.crypto.entity.purchase.Purchase;
import itu.crypto.firebase.firestore.generalisation.FirestoreChangeListener;
import itu.crypto.service.transaction.PurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PurchaseChangeListener extends FirestoreChangeListener<Purchase, PurchaseDocument> {

    private final PurchaseService purchaseService;

    public PurchaseChangeListener(Firestore firestore, PurchaseService purchaseService) {
        super(firestore, purchaseService, "purchase");
        this.purchaseService = purchaseService;
    }

    @Override
    protected Purchase toEntity(PurchaseDocument document) {
        Purchase entity = document.toEntity();

        entity.setAccountPurchaser(purchaseService.findAccountById(document.getAccountPurchaser().getId()));
        entity.setAccountSeller(purchaseService.findAccountById(document.getAccountSeller().getId()));
        entity.setSaleDetail(purchaseService.findSaleDetailById(document.getSaleDetailDocument().toEntity().getId()));

        return entity;
    }

    @Override
    protected Class<PurchaseDocument> getDocumentClass() {
        return PurchaseDocument.class;
    }

    @Override
    protected void updateDatabase(Purchase entity) {
        purchaseService.updateOrCreate(entity); // 🔥 Mise à jour ou insertion
    }

    @Override
    protected void deleteFromDatabase(String entityId) {
        purchaseService.deleteById(Integer.parseInt(entityId)); // 🔥 Suppression de la ligne
    }
}
