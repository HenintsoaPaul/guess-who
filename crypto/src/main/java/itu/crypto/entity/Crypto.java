package itu.crypto.entity;

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

}