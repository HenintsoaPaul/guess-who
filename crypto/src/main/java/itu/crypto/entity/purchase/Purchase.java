package itu.crypto.entity.purchase;

import itu.crypto.entity.sale.SaleDetail;
import itu.crypto.entity.account.Account;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchase")
@EntityListeners(PurchaseListener.class)
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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_account_purchaser", nullable = false)
    private Account accountPurchaser;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_account_seller", nullable = false)
    private Account accountSeller;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_sale_detail", nullable = false)
    private SaleDetail saleDetail;

    public Purchase(Integer id, LocalDateTime localDateTime, double totalPrice, double unitPrice, int quantityCrypto) {
        this.id = id;
        this.datePurchase = localDateTime;
        this.totalPrice = totalPrice;
        this.unitPrice = unitPrice;
        this.quantityCrypto = quantityCrypto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Purchase autrePurchase = (Purchase) o;
        return Double.compare(autrePurchase.totalPrice, totalPrice) == 0 &&
                Double.compare(autrePurchase.unitPrice, unitPrice) == 0 &&
                Double.compare(autrePurchase.quantityCrypto, quantityCrypto) == 0 &&

                Objects.equals(datePurchase, autrePurchase.datePurchase) &&
                Objects.equals(accountPurchaser, autrePurchase.accountPurchaser) &&
                Objects.equals(accountSeller, autrePurchase.accountSeller) &&
                Objects.equals(saleDetail, autrePurchase.saleDetail);
    }
}