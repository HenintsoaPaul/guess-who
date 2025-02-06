package itu.crypto.entity;

import itu.crypto.entity.account.Account;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@ToString
@Table(name = "sale")
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sale", nullable = false)
    private Integer id;

    @Column(name = "date_sale")
    private LocalDateTime dateSale;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_account", nullable = false)
    @ToString.Exclude
    private Account account;

}