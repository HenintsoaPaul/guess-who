package itu.crypto.firebase.firestore.purchase;

import itu.crypto.entity.sale.SaleDetail;
import itu.crypto.entity.crypto.Crypto;
import itu.crypto.firebase.firestore.generalisation.TimestampedDocument;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaleDetailDocument implements TimestampedDocument {

    private Integer id;
    private int quantity;
    private int quantityLeft;
    private Crypto crypto;
    private SaleDocument saleDocument;

    private String createdAt;
    private String updatedAt;

    public SaleDetailDocument(SaleDetail saleDetail) {
        this.id = saleDetail.getId();
        this.quantity = saleDetail.getQuantity();
        this.quantityLeft = saleDetail.getQuantityLeft();
        this.crypto = saleDetail.getCrypto();
        this.saleDocument = new SaleDocument(saleDetail.getSale());
    }

    public SaleDetail toEntity() {
        return new SaleDetail(
                id,
                quantity,
                quantityLeft,
                crypto,
                saleDocument.toEntity()
        );
    }
}
