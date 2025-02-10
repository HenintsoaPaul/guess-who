package itu.crypto.entity.purchase;

import itu.crypto.firebase.notification.FcmService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
public class PurchaseEventListener {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseEventListener.class);
    private final FcmService fcmService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handlePurchaseCreated(PurchaseCreatedEvent event) {
        try {
            // Placez ici votre logique métier post-insertion, par exemple l'envoi de notifications

            fcmService.send(event.getPurchase());
            logger.info("Traitement de l'événement pour la purchase avec id: {}", event.getPurchase().getId());
        } catch (Exception e) {
            // Gérer l'exception (par exemple, journaliser l'erreur)
            logger.error("Erreur lors du traitement de l'événement PurchaseCreatedEvent: {}", e.getMessage(), e);
            // Vous pouvez décider de notifier ou d'effectuer d'autres actions,
            // mais l'exception ne doit pas remonter pour affecter le commit déjà réalisé.
        }
    }
}
