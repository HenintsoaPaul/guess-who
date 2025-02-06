package itu.crypto.firebase.firestore.fund.pending;

import com.google.cloud.Timestamp;
import itu.crypto.entity.account.Account;
import itu.crypto.entity.fund.PendingMvFund;
import itu.crypto.entity.fund.PendingState;
import itu.crypto.entity.fund.TypeMvFund;
import itu.crypto.firebase.firestore.generalisation.TimestampedDocument;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneOffset;
import java.util.Date;

@Data
@NoArgsConstructor
public class PendingMvFundDocument implements TimestampedDocument {

    private Integer id;
    private Timestamp datePending;
    private Timestamp dateValidation;
    private double amount;

    private PendingState pendingState;
    private Account account;
    private TypeMvFund typeMvFund;

    private String createdAt;
    private String updatedAt;

    public PendingMvFundDocument(PendingMvFund pendingMvFund) {
        this.id = pendingMvFund.getId();
        this.datePending = Timestamp.of(Date.from(pendingMvFund.getDatePending().toInstant(ZoneOffset.UTC)));

        Object dd = pendingMvFund.getDateValidation() == null ?
                null : Timestamp.of(Date.from(pendingMvFund.getDateValidation().toInstant(ZoneOffset.UTC)));
        this.dateValidation = (Timestamp) dd;

        this.amount = pendingMvFund.getAmount();

        this.pendingState = pendingMvFund.getPendingState();
        this.account = pendingMvFund.getAccount();
        this.typeMvFund = pendingMvFund.getTypeMvFund();
    }

    public PendingMvFund toEntity() {
        return new PendingMvFund(
                id,
                datePending.toDate().toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime(),
                dateValidation.toDate().toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime(),
                amount,
                pendingState,
                account,
                typeMvFund
        );
    }
}
