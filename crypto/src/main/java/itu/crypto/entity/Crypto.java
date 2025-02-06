package itu.crypto.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
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

}