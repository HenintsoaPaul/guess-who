package itu.crypto.entity.fund;

import itu.crypto.firebase.firestore.fund.pending.PendingMvFundSyncService;
import itu.crypto.service.EmailService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class PendingMvFundListener {

    private final PendingMvFundSyncService pendingMvFundSyncService;
    private final EmailService emailService;

    @Autowired
    public PendingMvFundListener(@Lazy PendingMvFundSyncService pendingMvFundSyncService, @Lazy EmailService emailService) {
        this.pendingMvFundSyncService = pendingMvFundSyncService;
        this.emailService = emailService;
    }

    @PostPersist
    public void apresSauvegarde(PendingMvFund pmf) {
        pendingMvFundSyncService.saveAsDocument(pmf);
        this.emailService.writeEmailPendingMvFund(pmf);
    }

    @PostUpdate
    public void apresModification(PendingMvFund pmf) {
        pendingMvFundSyncService.updateAsDocument(pmf);
    }
}
