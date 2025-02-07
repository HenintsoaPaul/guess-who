package itu.crypto.firebase.firestore.fund.pending;

import com.google.cloud.firestore.Firestore;
import itu.crypto.entity.fund.PendingMvFund;
import itu.crypto.firebase.firestore.FirestoreChangeListener;
import itu.crypto.service.transaction.fund.PendingMvFundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PendingMvFundChangeListener extends FirestoreChangeListener<PendingMvFund, PendingMvFundDocument> {

    private final PendingMvFundService pendingMvFundService;

    public PendingMvFundChangeListener(Firestore firestore, PendingMvFundService pendingMvFundService) {
        super(firestore, pendingMvFundService, "pending_mv_fund");
        this.pendingMvFundService = pendingMvFundService;
    }


    @Override
    protected PendingMvFund toEntity(PendingMvFundDocument document) {
        return document.toEntity();
    }

    @Override
    protected Class<PendingMvFundDocument> getDocumentClass() {
        return PendingMvFundDocument.class;
    }

    @Override
    protected void updateDatabase(PendingMvFund entity) {
        pendingMvFundService.updateOrCreate(entity); // 🔥 Mise à jour ou insertion
    }

    @Override
    protected void deleteFromDatabase(String entityId) {
        pendingMvFundService.deleteById(Integer.parseInt(entityId)); // 🔥 Suppression de la ligne
    }
}
