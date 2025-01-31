package itu.crypto.controller;

import itu.crypto.dto.commission.CommissionTypeAnalysis;
import itu.crypto.entity.commission.CommissionPurchase;
import itu.crypto.entity.commission.CommissionRate;
import itu.crypto.enums.CommissionAnalysisType;
import itu.crypto.service.transaction.CommissionPurchaseService;
import itu.crypto.service.transaction.CommissionRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/commissions")
public class CommissionController {

    private final CommissionRateService commissionRateService;
    private final CommissionPurchaseService commissionPurchaseService;

    @GetMapping
    public String goToList(Model model,
                           @RequestParam(required = false, name = "typeAnalyse") CommissionAnalysisType analysisType,
                           @RequestParam(required = false) Integer idCrypto,
                           @RequestParam(required = false) String dateMin,
                           @RequestParam(required = false) String dateMax) {

        List<CommissionPurchase> commissionPurchases = commissionPurchaseService.findAllByDatePurchaseInRange(dateMin, dateMax);

        System.out.println("commissionPurchases: " + commissionPurchases);
        if (idCrypto != null && idCrypto != -1) {
            commissionPurchases = commissionPurchases.stream()
                    .filter(cp -> cp.getPurchase().getSaleDetail().getCrypto().getId().equals(idCrypto))
                    .toList();
        }

        if (analysisType == null) analysisType = CommissionAnalysisType.SUM_COMMISSION;
        List<CommissionTypeAnalysis> analyses = commissionPurchaseService.getAnalysis(analysisType, commissionPurchases);
        model.addAttribute("analyses", analyses);

        model.addAttribute("cryptos", commissionRateService.findAllCrypto());

        model.addAttribute("analyseTypes", CommissionAnalysisType.values());
        model.addAttribute("analyseType", analysisType);
        return "commissions/index";
    }

    @GetMapping("/rates")
    public String goToList(Model model) {
        model.addAttribute("commissionsRates", commissionRateService.findAll());
        model.addAttribute("currentCommissionRates", commissionRateService.findCurrentCommissions());

        return "commissions/rates/index";
    }

    @GetMapping("/edit")
    public String goToForm(Model model) {
        model.addAttribute("commissionRate", new CommissionRate());
        model.addAttribute("commissionTypes", commissionRateService.findAllTypes());
        return "commissions/edit";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("commission") CommissionRate commissionRate) throws Exception {
        commissionRateService.save(commissionRate);
        return "redirect:/commissions/edit";
    }
}
