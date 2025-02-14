package itu.crypto.firebase.firestore.crypto;

import com.google.cloud.Timestamp;
import itu.crypto.entity.account.Account;
import itu.crypto.entity.crypto.Crypto;
import itu.crypto.entity.crypto.CryptoFav;
import itu.crypto.firebase.firestore.generalisation.TimestampedDocument;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneOffset;
import java.util.Date;

@Data
@NoArgsConstructor
public class CryptoFavDocument implements TimestampedDocument {

    private Integer id;
    private Timestamp dateCryptoFav;
    private boolean onFav;
    private Crypto crypto;
    private Account account;

    private String createdAt;
    private String updatedAt;

    public CryptoFavDocument(CryptoFav cryptoFav) {
        this.id = cryptoFav.getId();
        this.crypto = cryptoFav.getCrypto();
        this.onFav = cryptoFav.isOnFav();
        this.account = cryptoFav.getAccount();
        this.dateCryptoFav = Timestamp.of(Date.from(cryptoFav.getDateCryptoFav().toInstant(ZoneOffset.UTC)));
    }

    public CryptoFav toEntity() {
        return new CryptoFav(
                id,
                dateCryptoFav.toDate().toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime(),
                onFav
        );
    }
}
