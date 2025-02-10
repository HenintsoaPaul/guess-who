package itu.crypto.enums;

import lombok.Getter;

@Getter
public enum CommissionType {
    SALE("Commission Vente"),
    PURCHASE("Commission Achat");

    private final String label;

    CommissionType(String lbl) {
        this.label = lbl;
    }
}
