package itu.crypto.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "type_mv_fund")
public class TypeMvFund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    public TypeMvFund() {}

    public TypeMvFund(String name) {
        this.name = name;
    }
}

