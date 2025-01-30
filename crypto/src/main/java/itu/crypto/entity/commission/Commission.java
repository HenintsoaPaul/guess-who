package itu.crypto.entity.commission;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "commission")
public class Commission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commission", nullable = false)
    private Integer id;

    @Column(name = "val", nullable = false, length = 250)
    private Double val;

    @Column(name = "daty", nullable = false, length = 250)
    private LocalDate addDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_commission_type", nullable = false)
    private CommissionType commissionType;

}