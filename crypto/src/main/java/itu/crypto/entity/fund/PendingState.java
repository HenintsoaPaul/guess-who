package itu.crypto.entity.fund;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pending_state")
public class PendingState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pending_state", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

//    @OneToMany
//    private Set<itu.crypto.entity.fund.PendingMvFund> pendingMvFunds = new LinkedHashSet<>();

}