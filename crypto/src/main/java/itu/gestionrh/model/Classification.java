package itu.gestionrh.model;

import jakarta.persistence.*;

@Entity
@Table(name = "classification")
public class Classification {

   @Id
    @Column(name = "id_classification")
    private String idClassification;  // Ensure this matches the database column

    @Column(name = "name")
    private String name;

    @Column(name = "level")
    private Integer level;

    @Column(name = "salary_min")
    private Double salaryMin;

    @Column(name = "salary_max")
    private Double salaryMax;

    public Classification() {}

    // Constructeur pour initialiser les attributs
    public Classification(String idClassification, String name, Integer level, Double salaryMin, Double salaryMax) {
        this.idClassification = idClassification;
        this.name = name;
        this.level = level;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
    }

    // Getters et Setters
    public String getIdClassification() {
        return idClassification;
    }

    public void setIdClassification(String idClassification) {
        this.idClassification = idClassification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Double getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(Double salaryMin) {
        this.salaryMin = salaryMin;
    }

    public Double getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(Double salaryMax) {
        this.salaryMax = salaryMax;
    }
}
