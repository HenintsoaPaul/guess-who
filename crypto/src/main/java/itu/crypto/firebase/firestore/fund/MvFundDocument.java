package itu.crypto.firebase.firestore.fund;

import com.google.cloud.Timestamp;
import itu.crypto.entity.fund.TypeMvFund;
import itu.crypto.entity.account.Account;
import itu.crypto.entity.fund.MvFund;
import itu.crypto.firebase.firestore.fund.pending.PendingMvFundDocument;
import itu.crypto.firebase.firestore.generalisation.TimestampedDocument;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneOffset;
import java.util.Date;

@Data
@NoArgsConstructor
public class MvFundDocument implements TimestampedDocument {

    private Integer id;
    private Timestamp dateMv;
    private double amount;

    private Integer idSource;
    private Account account;
    private TypeMvFund typeMvFund;

    private PendingMvFundDocument pendingMvFundDocument;

    private String createdAt;
    private String updatedAt;

    public MvFundDocument(MvFund mvFund) {
        this.id = mvFund.getId();
        this.dateMv = Timestamp.of(Date.from(mvFund.getDateMv().toInstant(ZoneOffset.UTC)));
        this.amount = mvFund.getAmount();

        this.idSource = mvFund.getIdSource();
        this.account = mvFund.getAccount();
        this.typeMvFund = mvFund.getTypeMvFund();

        this.pendingMvFundDocument = new PendingMvFundDocument(mvFund.getPendingMvFund());
    }

    public MvFund toEntity() {
        return new MvFund(
                id,
                dateMv.toDate().toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime(),
                amount,
                idSource,
                account,
                typeMvFund,
                pendingMvFundDocument.toEntity()
        );
    }
}
