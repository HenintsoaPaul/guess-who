package itu.crypto.entity.fund;

import itu.crypto.firebase.firestore.fund.MvFundSyncService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class MvFundListener {

    private final MvFundSyncService mvFundSyncService;

    @Autowired
    public MvFundListener(@Lazy MvFundSyncService mvFundSyncService) {
        this.mvFundSyncService = mvFundSyncService;
    }

    @PostPersist
    public void apresSauvegarde(MvFund mvFund) {
        System.out.println("Après insertion : " + mvFund);
        mvFundSyncService.saveAsDocument(mvFund);
    }

    @PostUpdate
    public void apresModification(MvFund mvFund) {
        System.out.println("Après udpate : " + mvFund);
        mvFundSyncService.updateAsDocument(mvFund);
    }
}
