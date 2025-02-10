package itu.crypto.firebase.firestore.fund;

import itu.crypto.entity.account.Account;
import itu.crypto.entity.fund.MvFund;
import itu.crypto.entity.fund.TypeMvFund;
import itu.crypto.firebase.firestore.fund.pending.PendingMvFundMobDocument;
import itu.crypto.firebase.firestore.generalisation.TimestampedDocument;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MvFundMobDocument implements TimestampedDocument {

    private Integer id;
    private String dateMv;
    private double amount;

    private Integer idSource;
    private Account account;
    private TypeMvFund typeMvFund;

    private PendingMvFundMobDocument pendingMvFundMobDocument;

    private String createdAt;
    private String updatedAt;

    public MvFund toEntity() {
        return new MvFund(
                id,
                null,
                amount,
                idSource,
                account,
                typeMvFund,
                pendingMvFundMobDocument.toEntity()
        );
    }
}
