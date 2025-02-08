package itu.crypto.firebase.firestore.crypto;

import itu.crypto.entity.crypto.Crypto;
import itu.crypto.firebase.firestore.generalisation.TimestampedDocument;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CryptoDocument implements TimestampedDocument {

    private Integer id;
    private String name;
    private String symbol;

    private String createdAt;
    private String updatedAt;

    public CryptoDocument(Crypto crypto) {
        this.id = crypto.getId();
        this.name = crypto.getName();
        this.symbol = crypto.getSymbol();
    }

    public Crypto toEntity() {
        return new Crypto(
                id,
                name,
                symbol
        );
    }
}
