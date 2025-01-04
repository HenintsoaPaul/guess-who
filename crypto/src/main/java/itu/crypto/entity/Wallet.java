package itu.crypto.entity;

import itu.crypto.model.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_wallet", nullable = false)
    private Integer id;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_crypto_currency", nullable = false)
    private CryptoCurrency idCryptoCurrency;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_account", nullable = false)
    private Account idAccount;

}