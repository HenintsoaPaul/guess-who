package itu.crypto.entity.purchase;

import itu.crypto.ApplicationEventPublisherHolder;
import itu.crypto.firebase.firestore.purchase.PurchaseSyncService;
import itu.crypto.firebase.notification.FcmService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class PurchaseListener {

    private final PurchaseSyncService purchaseSyncService;
    private final FcmService fcmService;

    @Autowired
    public PurchaseListener(
            @Lazy PurchaseSyncService purchaseSyncService,
            @Lazy FcmService fcmService
    ) {
        this.purchaseSyncService = purchaseSyncService;
        this.fcmService = fcmService;
    }

    @PostPersist
    public void apresSauvegarde(Purchase purchase) {
        purchaseSyncService.saveAsDocument(purchase);

        ApplicationEventPublisherHolder
                .getPublisher()
                .publishEvent(new PurchaseCreatedEvent(purchase));
    }

    @PostUpdate
    public void apresModification(Purchase purchase) {
        purchaseSyncService.updateAsDocument(purchase);
    }
}
