package itu.crypto.firebase.firestore.fav;

import com.google.cloud.Timestamp;
import itu.crypto.entity.account.Account;
import itu.crypto.entity.Crypto;
import itu.crypto.entity.fav.CryptoFav;
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
    private Crypto crypto;
    private Account account;

    private String createdAt;
    private String updatedAt;

    public CryptoFavDocument(CryptoFav cryptoFav) {
        this.id = cryptoFav.getId();
        this.crypto = cryptoFav.getCrypto();
        this.account = cryptoFav.getAccount();
        this.dateCryptoFav = Timestamp.of(Date.from(cryptoFav.getDateCryptoFav().toInstant(ZoneOffset.UTC)));
    }

    public CryptoFav toEntity() {
        return new CryptoFav(
                id,
                crypto,
                account,
                dateCryptoFav.toDate().toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime()
        );
    }
}
