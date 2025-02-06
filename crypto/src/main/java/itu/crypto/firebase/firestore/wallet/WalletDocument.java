package itu.crypto.firebase.firestore.wallet;

import itu.crypto.entity.Crypto;
import itu.crypto.entity.account.Account;
import itu.crypto.entity.wallet.Wallet;
import itu.crypto.firebase.firestore.generalisation.TimestampedDocument;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WalletDocument implements TimestampedDocument {

    private Integer id;
    private Integer quantity;
    private Crypto crypto;
    private Account account;

    private String createdAt;
    private String updatedAt;

    public WalletDocument(Wallet wallet) {
        this.id = wallet.getId();
        this.quantity = wallet.getQuantity();
        this.crypto = wallet.getCrypto();
        this.account = wallet.getAccount();
    }

    public Wallet toEntity() {
        return new Wallet(
                id,
                quantity,
                crypto,
                account
        );
    }
}
