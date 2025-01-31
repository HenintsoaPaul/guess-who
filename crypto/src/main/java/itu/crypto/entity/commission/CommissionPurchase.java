package itu.crypto.entity.commission;

import itu.crypto.entity.Purchase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "commission_purchase")
public class CommissionPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commission_purchase", nullable = false)
    private Integer id;

    @Column(name = "amount", nullable = false, length = 250)
    private Double amount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_commission_rate", nullable = false)
    private CommissionRate commissionRate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_purchase", nullable = false)
    private Purchase purchase;

}