package itu.crypto.firebase.firestore.account;

import itu.crypto.entity.account.Account;
import itu.crypto.firebase.firestore.generalisation.TimestampedDocument;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDocument implements TimestampedDocument {

    private Integer id;
    private String pseudo;
    private String accountImg;
    private String email;
    private String password;
    private String fcmToken;
    private double fund;

    private String createdAt;
    private String updatedAt;

    public AccountDocument(Account account) {
        this.id = account.getId();
        this.pseudo = account.getPseudo();
        this.email = account.getEmail();
        this.accountImg = account.getAccountImg();
        this.password = account.getPassword();
        this.fund = account.getFund();
        this.fcmToken = account.getFcmToken();
    }

    public Account toEntity() {
        return new Account(
                id,
                pseudo,
                accountImg,
                email,
                password,
                fund,
                fcmToken
        );
    }
}
