package itu.gestionrh.model;

import jakarta.persistence.*;

@Entity
@Table(name = "contracttype")
public class ContractType {

    @Id
    @Column(name = "id_contract_type")  // Colonne dans la base de données
    private String idContractType;
    
    @Column(name = "type_name")  // Nom du type de contrat
    private String typeName;

    @Column(name= "desce")
    private String desce;


    // Getters et Setters
    public String getIdContractType() {
        return idContractType;
    }

    public void setIdContractType(String idContractType) {
        this.idContractType = idContractType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    public String getDesce() {
        return desce;
    }

    public void setDesce(String desce) {
        this.desce = desce;
    }
}
