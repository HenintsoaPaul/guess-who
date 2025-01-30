package itu.crypto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "purchase")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_purchase", nullable = false)
    private Integer id;

    @Column(name = "date_purchase")
    private LocalDateTime datePurchase;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "quantity_crypto")
    private Integer quantityCrypto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_account_purchaser", nullable = false)
    private Account accountPurchaser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_account_seller", nullable = false)
    private Account accountSeller;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_sale_detail", nullable = false)
    private SaleDetail saleDetail;

}