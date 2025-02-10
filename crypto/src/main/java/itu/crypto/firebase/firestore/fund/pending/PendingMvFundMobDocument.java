package itu.crypto.firebase.firestore.fund.pending;

import itu.crypto.entity.account.Account;
import itu.crypto.entity.fund.PendingMvFund;
import itu.crypto.entity.fund.PendingState;
import itu.crypto.entity.fund.TypeMvFund;
import itu.crypto.firebase.firestore.generalisation.TimestampedDocument;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PendingMvFundMobDocument implements TimestampedDocument {

    private Integer id;
    private String datePending;
    private String dateValidation;
    private double amount;

    private PendingState pendingState;
    private Account account;
    private TypeMvFund typeMvFund;

    private String createdAt;
    private String updatedAt;

    public PendingMvFund toEntity() {
        return new PendingMvFund(
                id,
                null,
                null,
                amount
        );
    }
}
