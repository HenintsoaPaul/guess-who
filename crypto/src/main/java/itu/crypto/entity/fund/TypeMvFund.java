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

    public boolean deTypeDepot() {
        return this.id == 1;
    }

    public boolean deTypeRetrait() {
        return this.id == 2;
    }

    public boolean deTypeAchat() {
        return this.id == 3;
    }

    public boolean deTypeVente() {
        return this.id == 4;
    }
}