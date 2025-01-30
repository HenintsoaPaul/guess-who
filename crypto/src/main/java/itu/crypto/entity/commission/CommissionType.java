package itu.crypto.entity.commission;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "commission_type")
public class CommissionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commission_type", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

}