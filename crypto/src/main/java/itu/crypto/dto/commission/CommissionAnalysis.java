package itu.crypto.dto.commission;

import itu.crypto.entity.crypto.Crypto;
import itu.crypto.entity.commission.CommissionPurchase;
import itu.crypto.enums.CommissionAnalysisType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CommissionAnalysis {
    Crypto crypto;
    List<CommissionPurchase> commissionPurchases;
    double value;
    CommissionAnalysisType analysisType;
}
