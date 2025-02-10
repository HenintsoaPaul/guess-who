package itu.crypto.firebase.firestore.crypto;

import itu.crypto.entity.account.Account;
import itu.crypto.entity.crypto.Crypto;
import itu.crypto.entity.crypto.CryptoFav;
import itu.crypto.firebase.firestore.generalisation.TimestampedDocument;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CryptoFavMobDocument implements TimestampedDocument {

    private Integer id;
    private String dateCryptoFav;
    private boolean onFav;
    private Crypto crypto;
    private Account account;

    private String createdAt;
    private String updatedAt;

    public CryptoFav toEntity() {
        return new CryptoFav(
                id,
                null,
                onFav
        );
    }
}
