package itu.crypto.entity.crypto;

import itu.crypto.entity.account.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "crypto_fav")
@EntityListeners(CryptoFavListener.class)
public class CryptoFav {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_crypto_fav", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_crypto", nullable = false)
    private Crypto crypto;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_account", nullable = false)
    private Account account;

    @Column(name = "date_crypto_fav")
    private LocalDateTime dateCryptoFav;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CryptoFav autreCryptoFav = (CryptoFav) o;

        return Objects.equals(crypto, autreCryptoFav.crypto) &&
                Objects.equals(account, autreCryptoFav.account) &&
                Objects.equals(dateCryptoFav, autreCryptoFav.dateCryptoFav);
    }
}