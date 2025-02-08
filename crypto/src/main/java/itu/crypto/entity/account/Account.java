package itu.crypto.entity.account;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
@EntityListeners(AccountListener.class)
public class Account {

    @Id
    @Column(name = "id_account", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pseudo", nullable = false, length = 250)
    private String pseudo;

    @Column(name = "account_img", length = 250)
    private String account_img;

    @Column(name = "email", nullable = false, length = 250)
    private String email;

    @Column(name = "password", nullable = false, length = 250)
    private String password;

    @Column(name = "fund", nullable = false)
    private double fund;

    public Account(Integer id, String pseudo, String mail, String mypassword, Double fund) {
        this.id = id;
        this.pseudo = pseudo;
        this.email = mail;
        this.password = mypassword;
        this.fund = fund;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account autreAccount = (Account) o;
        return Double.compare(autreAccount.fund, fund) == 0;
    }
}
