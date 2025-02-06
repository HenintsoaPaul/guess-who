package itu.crypto.entity.fund;

import itu.crypto.firebase.firestore.fund.pending.PendingMvFundSyncService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class PendingMvFundListener {

    private final PendingMvFundSyncService pendingMvFundSyncService;

    @Autowired
    public PendingMvFundListener(@Lazy PendingMvFundSyncService pendingMvFundSyncService) {
        this.pendingMvFundSyncService = pendingMvFundSyncService;
    }

    @PostPersist
    public void apresSauvegarde(PendingMvFund pmf) {
        pendingMvFundSyncService.saveAsDocument(pmf);
    }

    @PostUpdate
    public void apresModification(PendingMvFund pmf) {
        pendingMvFundSyncService.updateAsDocument(pmf);
    }
}
