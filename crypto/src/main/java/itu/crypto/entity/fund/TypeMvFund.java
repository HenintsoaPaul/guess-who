package itu.crypto.entity.fund;

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
@Table(name = "type_mv_fund")
public class TypeMvFund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_mv_fund", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

}