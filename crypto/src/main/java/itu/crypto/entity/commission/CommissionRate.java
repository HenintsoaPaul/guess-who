package itu.crypto.entity.commission;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "commission_rate")
public class CommissionRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commission_rate", nullable = false)
    private Integer id;

    @Column(name = "rate", nullable = false)
    private Double rate;

    @Column(name = "add_date", nullable = false, length = 250)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime addDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_commission_type", nullable = false)
    private CommissionType commissionType;

}