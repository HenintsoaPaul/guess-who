package itu.crypto.firebase.firestore.fund.pending;

import com.google.cloud.firestore.Firestore;
import itu.crypto.entity.fund.PendingMvFund;
import itu.crypto.firebase.firestore.generalisation.FirestoreChangeListener;
import itu.crypto.service.DateService;
import itu.crypto.service.transaction.fund.PendingMvFundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PendingMvFundChangeListener extends FirestoreChangeListener<PendingMvFund, PendingMvFundMobDocument> {

    private final PendingMvFundService pendingMvFundService;
    private final DateService dateService;

    public PendingMvFundChangeListener(Firestore firestore, PendingMvFundService pendingMvFundService, DateService dateService) {
        super(firestore, pendingMvFundService, "pending_mv_fund");
        this.pendingMvFundService = pendingMvFundService;
        this.dateService = dateService;
    }


    @Override
    protected PendingMvFund toEntity(PendingMvFundMobDocument document) {
        PendingMvFund pmf = document.toEntity();

        pmf.setDatePending(dateService.strToLocalDateTime(document.getDatePending()));
        pmf.setDateValidation(dateService.strToLocalDateTime(document.getDateValidation()));

        pmf.setPendingState(pendingMvFundService.findPendingStateById(pmf.getPendingState().getId()));
        pmf.setAccount(pendingMvFundService.findAccountById(pmf.getAccount().getId()));
        pmf.setTypeMvFund(pendingMvFundService.findTypeMvFundById(pmf.getTypeMvFund().getId()));

        return pmf;
    }

    @Override
    protected Class<PendingMvFundMobDocument> getDocumentClass() {
        return PendingMvFundMobDocument.class;
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
