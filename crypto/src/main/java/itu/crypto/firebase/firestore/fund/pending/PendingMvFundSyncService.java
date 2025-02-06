package itu.crypto.firebase.firestore.fund.pending;

import com.google.cloud.firestore.Firestore;
import itu.crypto.entity.fund.PendingMvFund;
import itu.crypto.firebase.firestore.generalisation.GenericSyncService;
import itu.crypto.service.transaction.fund.PendingMvFundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PendingMvFundSyncService extends GenericSyncService<PendingMvFund, PendingMvFundDocument> {

    public PendingMvFundSyncService(Firestore firestore, PendingMvFundService pendingMvFundService) {
        super(firestore, pendingMvFundService, "pending_mv_fund");
    }

    @Override
    protected PendingMvFundDocument toDocument(PendingMvFund entity) {
        return new PendingMvFundDocument(entity);
    }

    @Override
    protected PendingMvFund toEntity(PendingMvFundDocument document) {
        return document.toEntity();
    }

    @Override
    protected String getEntityId(PendingMvFund entity) {
        return entity.getId().toString();
    }

    @Override
    protected Class<PendingMvFundDocument> getDocumentClass() {
        return PendingMvFundDocument.class;
    }
}
