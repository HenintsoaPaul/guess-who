package itu.crypto.enums;

import lombok.Getter;

@Getter
public enum CoursAnalysisType {
    MAX_COURS("Max des cours"),
    MIN_COURS("Min des cours"),
    AVG_COURS("Moyenne des cours"),
    ECART_TYPE_COURS("Ecart-type des cours"),
    FIRST_QUARTILE_COURS("1er quartile des cours");

    private final String label;

    CoursAnalysisType(String lbl) {
        this.label = lbl;
    }
}
