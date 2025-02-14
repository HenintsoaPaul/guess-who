package itu.crypto.firebase.firestore.purchase;

import com.google.cloud.Timestamp;
import itu.crypto.entity.account.Account;
import itu.crypto.entity.purchase.Purchase;
import itu.crypto.firebase.firestore.generalisation.TimestampedDocument;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneOffset;
import java.util.Date;

@Data
@NoArgsConstructor
public class PurchaseDocument implements TimestampedDocument {

    private Integer id;
    private Timestamp datePurchase;
    private double totalPrice;
    private double unitPrice;
    private int quantityCrypto;

    private Account accountPurchaser;
    private Account accountSeller;

    private SaleDetailDocument saleDetailDocument;

    private String createdAt;
    private String updatedAt;

    public PurchaseDocument(Purchase purchase) {
        this.id = purchase.getId();
        this.datePurchase = Timestamp.of(Date.from(purchase.getDatePurchase().toInstant(ZoneOffset.UTC)));
        this.totalPrice = purchase.getTotalPrice();
        this.unitPrice = purchase.getUnitPrice();
        this.quantityCrypto = purchase.getQuantityCrypto();

        this.accountPurchaser = purchase.getAccountPurchaser();
        this.accountSeller = purchase.getAccountSeller();

        this.saleDetailDocument = new SaleDetailDocument(purchase.getSaleDetail());
    }

    public Purchase toEntity() {
        return new Purchase(
                id,
                datePurchase.toDate().toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime(),
                totalPrice,
                unitPrice,
                quantityCrypto
        );
    }
}
