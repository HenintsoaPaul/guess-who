package itu.crypto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "sale_detail")
public class SaleDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sale_detail", nullable = false)
    private Integer id;

    @Column(name = "quantity_crypto")
    private Integer quantity;

    @Column(name = "quantity_left")
    private Integer quantityLeft;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_crypto", nullable = false)
    private Crypto crypto;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_sale", nullable = false)
    private Sale sale;

}