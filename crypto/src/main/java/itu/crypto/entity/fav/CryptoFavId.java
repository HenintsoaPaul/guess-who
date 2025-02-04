package itu.crypto.entity.fav;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import org.hibernate.Hibernate;

import java.util.Objects;

@Data
@Embeddable
public class CryptoFavId implements java.io.Serializable {
    private static final long serialVersionUID = -4039470252812898661L;

    @Column(name = "id_crypto", nullable = false)
    private Integer idCrypto;

    @Column(name = "id_account", nullable = false)
    private Integer idAccount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CryptoFavId entity = (CryptoFavId) o;
        return Objects.equals(this.idAccount, entity.idAccount) &&
                Objects.equals(this.idCrypto, entity.idCrypto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAccount, idCrypto);
    }

}