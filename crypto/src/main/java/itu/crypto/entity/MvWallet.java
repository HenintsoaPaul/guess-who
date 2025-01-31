package itu.crypto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "mv_wallet")
public class MvWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mv_wallet", nullable = false)
    private Integer id;

    @Column(name = "quantity_crypto")
    private Integer quantity;

    @Column(name = "date_mv")
    private LocalDate dateMv;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_wallet", nullable = false)
    private Wallet wallet;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_type_mv_wallet", nullable = false)
    private TypeMvWallet typeMvWallet;

}