package itu.crypto.firebase.firestore.fund;

import com.google.cloud.firestore.Firestore;
import itu.crypto.entity.fund.MvFund;
import itu.crypto.firebase.firestore.generalisation.GenericSyncService;
import itu.crypto.service.transaction.fund.MvFundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MvFundSyncService extends GenericSyncService<MvFund, MvFundDocument> {

    public MvFundSyncService(Firestore firestore, MvFundService mvFundService) {
        super(firestore, mvFundService, "mv_fund");
    }

    @Override
    protected MvFundDocument toDocument(MvFund entity) {
        return new MvFundDocument(entity);
    }

    @Override
    protected MvFund toEntity(MvFundDocument document) {
        return document.toEntity();
    }

    @Override
    protected String getEntityId(MvFund entity) {
        return entity.getId().toString();
    }

    @Override
    protected Class<MvFundDocument> getDocumentClass() {
        return MvFundDocument.class;
    }
}
