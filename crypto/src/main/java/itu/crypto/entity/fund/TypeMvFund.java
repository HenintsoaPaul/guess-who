package itu.crypto.entity.fund;

import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@Entity
@Table(name = "type_mv_fund")
@NoArgsConstructor
@AllArgsConstructor
public class TypeMvFund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_mv_fund", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

}