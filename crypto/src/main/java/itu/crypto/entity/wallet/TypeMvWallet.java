package itu.crypto.entity.wallet;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "type_mv_wallet")
public class TypeMvWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_mv_wallet", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

}