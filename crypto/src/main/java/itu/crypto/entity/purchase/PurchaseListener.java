package itu.crypto.entity.purchase;

import itu.crypto.firebase.firestore.purchase.PurchaseSyncService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PurchaseListener {

    private final PurchaseSyncService purchaseSyncService;

    @Autowired
    public PurchaseListener(
            @Lazy PurchaseSyncService purchaseSyncService
    ) {
        this.purchaseSyncService = purchaseSyncService;
    }

    @PostPersist
    public void apresSauvegarde(Purchase purchase) {
        purchaseSyncService.saveAsDocument(purchase);
    }

    @PostUpdate
    public void apresModification(Purchase purchase) {
        purchaseSyncService.updateAsDocument(purchase);
    }
}
