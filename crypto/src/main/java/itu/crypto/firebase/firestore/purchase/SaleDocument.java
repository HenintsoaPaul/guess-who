package itu.crypto.firebase.firestore.purchase;

import com.google.cloud.Timestamp;
import itu.crypto.entity.Sale;
import itu.crypto.entity.account.Account;
import itu.crypto.firebase.firestore.generalisation.TimestampedDocument;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneOffset;
import java.util.Date;

@Data
@NoArgsConstructor
public class SaleDocument implements TimestampedDocument {

    private Integer id;
    private Timestamp dateSale;
    private Account account;

    private String createdAt;
    private String updatedAt;

    public SaleDocument(Sale sale) {
        this.id = sale.getId();
        this.dateSale = Timestamp.of(Date.from(sale.getDateSale().toInstant(ZoneOffset.UTC)));
        this.account = sale.getAccount();
    }

    public Sale toEntity() {
        return new Sale(
                id,
                dateSale.toDate().toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime(),
                account
        );
    }
}
