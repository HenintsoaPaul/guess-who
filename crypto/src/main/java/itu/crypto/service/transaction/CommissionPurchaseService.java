package itu.crypto.service.transaction;

import itu.crypto.dto.commission.CommissionTypeAnalysis;
import itu.crypto.entity.purchase.Purchase;
import itu.crypto.entity.commission.CommissionPurchase;
import itu.crypto.enums.CommissionAnalysisType;
import itu.crypto.enums.CommissionType;
import itu.crypto.repository.transaction.CommissionPurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommissionPurchaseService {

    private final CommissionPurchaseRepository commissionPurchaseRepository;
    private final PurchaseService purchaseService;

    public List<CommissionPurchase> findAll() {
        return commissionPurchaseRepository.findAll();
    }

    /**
     * Recuperer les commissions de vente dans une intervalle de date en se referant
     * a la vente mere de ces commissions.
     */
    public List<CommissionPurchase> findAllByDatePurchaseInRange(String dateMin, String dateMax) {
        List<Purchase> purchases = purchaseService.findAllByDatePurchaseInRange(dateMin, dateMax);

        return findAll().stream()
                .filter(c -> purchases.contains(c.getPurchase()))
                .toList();
    }

    public List<CommissionTypeAnalysis> getAnalysis(CommissionAnalysisType analysisType, List<CommissionPurchase> commissionPurchases) {
        List<CommissionTypeAnalysis> analysesByCommissionType = new ArrayList<>(); // vente | achat
        for (CommissionType cType : CommissionType.values()) {
            List<CommissionPurchase> temp = commissionPurchases.stream()
                    .filter(comm -> comm.getCommissionRate().getCommissionType().getName().equals(cType.getLabel()))
                    .toList();

            double somme = temp.stream()
                    .mapToDouble(CommissionPurchase::getAmount)
                    .sum();

            if (analysisType == CommissionAnalysisType.AVG_COMMISSION) {
                somme /= temp.size();
            }

            analysesByCommissionType.add(new CommissionTypeAnalysis(cType, temp, somme));
        }
        return analysesByCommissionType;
    }
}

