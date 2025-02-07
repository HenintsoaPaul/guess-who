package itu.crypto.firebase.firestore.fund;

import com.google.cloud.firestore.Firestore;
import itu.crypto.entity.fund.MvFund;
import itu.crypto.firebase.firestore.FirestoreChangeListener;
import itu.crypto.service.transaction.fund.MvFundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MvFundChangeListener extends FirestoreChangeListener<MvFund, MvFundDocument> {

    private final MvFundService mvFundService;

    public MvFundChangeListener(Firestore firestore, MvFundService mvFundService) {
        super(firestore, mvFundService, "mv_fund");
        this.mvFundService = mvFundService;
    }

    @Override
    protected MvFund toEntity(MvFundDocument document) {
        return document.toEntity();
    }

    @Override
    protected Class<MvFundDocument> getDocumentClass() {
        return MvFundDocument.class;
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
