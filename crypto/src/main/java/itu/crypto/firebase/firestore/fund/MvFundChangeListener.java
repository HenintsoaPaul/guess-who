package itu.crypto.firebase.firestore.fund;

import com.google.cloud.firestore.Firestore;
import itu.crypto.entity.fund.MvFund;
import itu.crypto.firebase.firestore.generalisation.FirestoreChangeListener;
import itu.crypto.service.DateService;
import itu.crypto.service.transaction.fund.MvFundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MvFundChangeListener extends FirestoreChangeListener<MvFund, MvFundMobDocument> {

    private final MvFundService mvFundService;
    private final DateService dateService;

    public MvFundChangeListener(Firestore firestore, MvFundService mvFundService) {
        super(firestore, mvFundService, "mv_fund");
        this.mvFundService = mvFundService;
        this.dateService = new DateService();
    }

    @Override
    protected MvFund toEntity(MvFundMobDocument document) {
        MvFund mvFund = document.toEntity();

        mvFund.setDateMv(dateService.strToLocalDateTime(document.getDateMv()));

        return mvFund;
    }

    @Override
    protected Class<MvFundMobDocument> getDocumentClass() {
        return MvFundMobDocument.class;
    }

    @Override
    protected void updateDatabase(MvFund entity) {
        mvFundService.updateOrCreate(entity); // 🔥 Mise à jour ou insertion
    }

    @Override
    protected void deleteFromDatabase(String entityId) {
        mvFundService.deleteById(Integer.parseInt(entityId)); // 🔥 Suppression de la ligne
    }
}
