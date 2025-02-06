package itu.crypto.entity.wallet;

import itu.crypto.entity.Crypto;
import itu.crypto.entity.account.Account;
import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@Entity
@Table(name = "wallet")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(WalletListener.class)
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_wallet", nullable = false)
    private Integer id;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_crypto", nullable = false)
    private Crypto crypto;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_account", nullable = false)
    private Account account;

}