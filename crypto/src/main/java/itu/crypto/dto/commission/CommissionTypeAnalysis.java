package itu.crypto.dto.commission;

import itu.crypto.entity.commission.CommissionPurchase;
import itu.crypto.enums.CommissionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CommissionTypeAnalysis {
    CommissionType commissionType;
    List<CommissionPurchase> commissionPurchases;
    double value;
}
