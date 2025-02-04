package itu.crypto.entity.fav;

import itu.crypto.entity.Account;
import itu.crypto.entity.Crypto;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "crypto_fav")
public class CryptoFav {
    @EmbeddedId
    private CryptoFavId id;

    @MapsId("idCrypto")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_crypto", nullable = false)
    private Crypto idCrypto;

    @MapsId("idAccount")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_account", nullable = false)
    private Account idAccount;

    @Column(name = "date_fav")
    private LocalDateTime dateFav;

}