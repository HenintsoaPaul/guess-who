package itu.crypto.service.transaction;

import itu.crypto.dto.CommissionAnalysis;
import itu.crypto.entity.Crypto;
import itu.crypto.entity.Purchase;
import itu.crypto.entity.commission.CommissionPurchase;
import itu.crypto.enums.CommissionAnalysisType;
import itu.crypto.repository.transaction.CommissionPurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<CommissionAnalysis> getAnalysis(CommissionAnalysisType analysisType, List<CommissionPurchase> commissionPurchases) {
        // grouper par crypto
        List<Crypto> keys = new ArrayList<>();
        for (CommissionPurchase commission : commissionPurchases) {
            Crypto kk = commission.getPurchase().getSaleDetail().getCrypto();
            if (!keys.contains(kk)) {
                keys.add(kk);
            }
        }

        List<CommissionAnalysis> analyses = new ArrayList<>();
        for (Crypto key : keys) {
            List<CommissionPurchase> temp = commissionPurchases.stream()
                    .filter(comm -> comm.getPurchase().getSaleDetail().getCrypto().equals(key))
                    .toList();

            double somme = temp.stream()
                    .mapToDouble(CommissionPurchase::getAmount)
                    .sum();

            if (analysisType == CommissionAnalysisType.AVG_COMMISSION) {
                somme /= temp.size();
            }

            analyses.add(new CommissionAnalysis(key, temp, somme, analysisType));
        }

        return analyses;
    }
}

