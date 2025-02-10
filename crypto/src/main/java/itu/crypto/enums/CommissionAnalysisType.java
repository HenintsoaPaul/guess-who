package itu.crypto.enums;

import lombok.Getter;

@Getter
public enum CommissionAnalysisType {
    SUM_COMMISSION("Somme des commissions"),
    AVG_COMMISSION("Moyenne des commissions");

    private final String label;

    CommissionAnalysisType(String lbl) {
        this.label = lbl;
    }
}
