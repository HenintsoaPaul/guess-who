package itu.crypto.entity.fund;

import com.google.firebase.messaging.FirebaseMessagingException;
import itu.crypto.firebase.firestore.fund.pending.PendingMvFundSyncService;
import itu.crypto.service.transaction.fund.MvFundService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class PendingMvFundListener {

    private final MvFundService mvFundService;
    private final PendingMvFundSyncService pendingMvFundSyncService;

    @Autowired
    public PendingMvFundListener(@Lazy PendingMvFundSyncService pendingMvFundSyncService,
                                 @Lazy MvFundService mvFundService) {
        this.pendingMvFundSyncService = pendingMvFundSyncService;
        this.mvFundService = mvFundService;
    }

    @PostPersist
    public void apresSauvegarde(PendingMvFund pmf) throws FirebaseMessagingException {
        System.out.println("Après insertion : " + pmf);

        if (pmf.isValidated()) {
            mvFundService.addFromPending(pmf);
        }

        pendingMvFundSyncService.saveAsDocument(pmf);
    }

    @PostUpdate
    public void apresModification(PendingMvFund pmf) throws FirebaseMessagingException {
        System.out.println("Après udpate : " + pmf);

        if (pmf.isValidated()) {
            mvFundService.addFromPending(pmf);
        }

        pendingMvFundSyncService.updateAsDocument(pmf);
    }
}
