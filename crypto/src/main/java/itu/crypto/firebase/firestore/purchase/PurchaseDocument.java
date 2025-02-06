package itu.crypto.firebase.firestore.purchase;

import com.google.cloud.Timestamp;
import itu.crypto.entity.account.Account;
import itu.crypto.entity.Purchase;
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

    private Integer idAccountPurchaser;
    private Integer idAccountSeller;
    private Integer idCrypto;
    private Integer idSaleDetail;

    private Account accountPurchaser;
    private Account accountSeller;

    // Warning: Tsy mety ilay daty mifampiditra fa mila de type google
//    private SaleDetail saleDetail;

    private String createdAt;
    private String updatedAt;

    public PurchaseDocument(Purchase purchase) {
        this.id = purchase.getId();
        this.datePurchase = Timestamp.of(Date.from(purchase.getDatePurchase().toInstant(ZoneOffset.UTC)));
        this.totalPrice = purchase.getTotalPrice();
        this.unitPrice = purchase.getUnitPrice();
        this.quantityCrypto = purchase.getQuantityCrypto();

        this.idAccountPurchaser = purchase.getAccountPurchaser().getId();
        this.idAccountSeller = purchase.getAccountSeller().getId();
        this.idCrypto = purchase.getSaleDetail().getCrypto().getId();
        this.idSaleDetail = purchase.getSaleDetail().getId();

        this.accountPurchaser = purchase.getAccountPurchaser();
        this.accountSeller = purchase.getAccountSeller();
//        this.saleDetail = purchase.getSaleDetail();
    }

    public Purchase toEntity() {
        return new Purchase(
                id,
                datePurchase.toDate().toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime(),
                totalPrice,
                unitPrice,
                quantityCrypto,
                accountPurchaser,
                accountSeller,
                idSaleDetail
        );
    }
}
