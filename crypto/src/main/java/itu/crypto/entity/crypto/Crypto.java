package itu.crypto.entity.crypto;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(CryptoListener.class)
@Table(name = "crypto")
public class Crypto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_crypto", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Column(name = "symbol", nullable = false, length = 5)
    private String symbol;

    public String sout() {
        return name + " (" + symbol + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return Objects.equals(id, ((Crypto) o).id) &&
                Objects.equals(name, ((Crypto) o).name) &&
                Objects.equals(symbol, ((Crypto) o).symbol);
    }
}