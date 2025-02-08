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
    private double fund;

    private String createdAt;
    private String updatedAt;

    public AccountDocument(Account account) {
        this.id = account.getId();
        this.pseudo = account.getPseudo();
        this.email = account.getEmail();
        this.password = account.getPassword();
        this.fund = account.getFund();
    }

    public Account toEntity() {
        return new Account(
                id,
                pseudo,
                accountImg,
                email,
                password,
                fund
        );
    }
}
