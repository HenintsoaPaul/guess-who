package itu.crypto.entity.account;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "id_account", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pseudo", nullable = false, length = 250)
    private String pseudo;

    @Column(name = "email", nullable = false, length = 250)
    private String email;

    @Column(name = "fund", nullable = false)
    private double fund;

}
