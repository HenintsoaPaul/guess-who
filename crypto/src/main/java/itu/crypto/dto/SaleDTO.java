package itu.crypto.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaleDTO {
    private String crypto;
    private double quantite;

    public SaleDTO(String crypto, double quantite) {
        this.quantite = quantite;
        this.crypto = crypto;
    }
}
