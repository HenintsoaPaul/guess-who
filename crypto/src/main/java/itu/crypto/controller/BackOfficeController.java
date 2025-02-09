package itu.crypto.controller;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import itu.crypto.dto.commission.CommissionTypeAnalysis;
import itu.crypto.entity.commission.CommissionPurchase;
import itu.crypto.entity.commission.CommissionRate;
import itu.crypto.entity.cours.Cours;

import itu.crypto.entity.fund.PendingMvFund;
import itu.crypto.entity.fund.PendingMvFundException;
import itu.crypto.entity.wallet.MvWallet;
import itu.crypto.enums.CommissionAnalysisType;
import itu.crypto.enums.CoursAnalysisType;
import itu.crypto.service.CoursService;
import itu.crypto.service.account.AccountOverviewService;
import itu.crypto.service.transaction.CommissionPurchaseService;
import itu.crypto.service.transaction.CommissionRateService;

import itu.crypto.service.transaction.fund.PendingMvFundService;
import itu.crypto.service.transaction.wallet.MvWalletService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/back-office")
public class BackOfficeController {
    @GetMapping
    public String goToSample(Model model) {
        return "back_office/index";
    }

    private final CoursService coursService;
    private final CommissionRateService commissionRateService;
    private final CommissionPurchaseService commissionPurchaseService;
    private final AccountOverviewService accountOverviewService;
    private final MvWalletService mvWalletService;

    private final PendingMvFundService pendingMvFundService;

    // Cours Crypto
    @GetMapping("/cours")
    public String goToList(Model model,
            @RequestParam(required = false, name = "typeAnalyse") CoursAnalysisType analysisType,
            @RequestParam(required = false) Integer idCrypto,
            @RequestParam(required = false) String dateMin,
            @RequestParam(required = false) String dateMax) {

        List<Cours> cours = coursService.findAllByDateInInterval(dateMin, dateMax);

        if (idCrypto != null && idCrypto != -1) {
            cours = cours.stream()
                    .filter(cp -> cp.getCrypto().getId().equals(idCrypto))
                    .toList();
        }

        if (analysisType == null) {
            analysisType = CoursAnalysisType.MAX_COURS;
            model.addAttribute("analyses", cours);
        } else {
            List<Cours> analyses = coursService.getAnalysis(analysisType, cours);
            model.addAttribute("analyses", analyses);
        }

        model.addAttribute("cryptos", coursService.findAllCrypto());
        model.addAttribute("analyseTypes", CoursAnalysisType.values());
        model.addAttribute("analyseType", analysisType);
        return "back_office/cours";
    }

    // Commission
    @GetMapping("/commissions")
    public String goToList(Model model,
            @RequestParam(required = false, name = "typeAnalyse") CommissionAnalysisType analysisType,
            @RequestParam(required = false) Integer idCrypto,
            @RequestParam(required = false) String dateMin,
            @RequestParam(required = false) String dateMax) {

        List<CommissionPurchase> commissionPurchases = commissionPurchaseService.findAllByDatePurchaseInRange(dateMin,
                dateMax);

        if (idCrypto != null && idCrypto != -1) {
            commissionPurchases = commissionPurchases.stream()
                    .filter(cp -> cp.getPurchase().getSaleDetail().getCrypto().getId().equals(idCrypto))
                    .toList();
        }

        if (analysisType == null)
            analysisType = CommissionAnalysisType.SUM_COMMISSION;
        List<CommissionTypeAnalysis> analyses = commissionPurchaseService.getAnalysis(analysisType,
                commissionPurchases);
        model.addAttribute("analyses", analyses);

        model.addAttribute("cryptos", commissionRateService.findAllCrypto());

        model.addAttribute("analyseTypes", CommissionAnalysisType.values());
        model.addAttribute("analyseType", analysisType);
        return "back_office/commissions/index";
    }

    @GetMapping("/commissions/tarifs")
    public String goToList(Model model) {
        model.addAttribute("commissionsRates", commissionRateService.findAll());
        model.addAttribute("currentCommissionRates", commissionRateService.findCurrentCommissions());

        return "back_office/commissions/tarifs";
    }

    @GetMapping("/commissions/modif")
    public String goToForm(Model model) {
        model.addAttribute("commissionRate", new CommissionRate());
        model.addAttribute("commissionTypes", commissionRateService.findAllTypes());
        return "back_office/commissions/modif";
    }

    @PostMapping("/commissions/save")
    public String save(@ModelAttribute("commission") CommissionRate commissionRate) {
        commissionRateService.save(commissionRate);
        return "redirect:/back-office/commissions/modif";
    }

    //Overview
    @GetMapping("/overview")
    public String getAll(Model model,
            @RequestParam(required = false) String dateMin,
            @RequestParam(required = false) String dateMax) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime dmin = (dateMin == null || dateMin.isEmpty()) ? null
                : LocalDateTime.parse(dateMin + " 00:00:00", formatter),
                dmax = (dateMax == null || dateMax.isEmpty()) ? null
                        : LocalDateTime.parse(dateMax + " 23:59:59", formatter);

        model.addAttribute("overviews", accountOverviewService.findAllAccountOverview(dmin, dmax));
        return "back_office/overview";
    }

    // Operation
    @GetMapping("/operations")
    public String getAll(Model model,
            @RequestParam(required = false) Integer idCrypto,
            @RequestParam(required = false) Integer idAccount,
            @RequestParam(required = false) String dateMin,
            @RequestParam(required = false) String dateMax) {
        List<MvWallet> mvWallets = mvWalletService.findAll();

        filterData(model, idCrypto, dateMin, dateMax, mvWallets);

        if (idAccount != null) {
            mvWallets.retainAll(mvWalletService.findAllByIdAccount(idAccount));
        }

        model.addAttribute("mvWallets", mvWallets);
        model.addAttribute("cryptos", mvWalletService.findAllCrypto());
        model.addAttribute("accounts", mvWalletService.findAllAccount());
        return "/back_office/operations/index";
    }

    @GetMapping("/operations/account/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        model.addAttribute("cryptos", mvWalletService.findAllCrypto());
        model.addAttribute("mvWallets", mvWalletService.findAllByIdAccount(id));
        model.addAttribute("idAccount", id);
        model.addAttribute("account", mvWalletService.findAccountById(id));
        return "/back_office/operations/by-account";
    }

    @GetMapping("/operations/account")
    public String detailFiltered(Model model,
                                 @RequestParam(required = false) Integer idAccount,
                                 @RequestParam(required = false) Integer idCrypto,
                                 @RequestParam(required = false) String dateMin,
                                 @RequestParam(required = false) String dateMax) {
        List<MvWallet> mvWallets = mvWalletService.findAllByIdAccount(idAccount);

        filterData(model, idCrypto, dateMin, dateMax, mvWallets);

        model.addAttribute("cryptos", mvWalletService.findAllCrypto());
        model.addAttribute("mvWallets", mvWallets);
        model.addAttribute("idAccount", idAccount);
        model.addAttribute("account", mvWalletService.findAccountById(idAccount));

        return "/back_office/operations/by-account";
    }

    private void filterData(Model model,
                            @RequestParam(required = false) Integer idCrypto,
                            @RequestParam(required = false) String dateMin,
                            @RequestParam(required = false) String dateMax,
                            List<MvWallet> mvWallets) {
        LocalDate dd;
        if (dateMin != null && !dateMin.isEmpty()) {
            dd = LocalDate.parse(dateMin, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            mvWallets.retainAll(mvWalletService.findAllAfterDateMin(dd));
            model.addAttribute("dateMin", dateMin);
        }
        if (dateMax != null && !dateMax.isEmpty()) {
            dd = LocalDate.parse(dateMax, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            mvWallets.retainAll(mvWalletService.findAllBeforeDateMax(dd));
            model.addAttribute("dateMax", dateMax);
        }
        if (idCrypto != null) {
            mvWallets.retainAll(mvWalletService.findAllByIdCrypto(idCrypto));
        }
    }

    // Graphe crypto 
    @GetMapping("/crypto-graphe")
    public String goToCryptoGraphe(Model model) {
        return "/back_office/cryptoGraphe";
    }


    // Validation transaction 
     @GetMapping("/transactions")
    public String gotoListPendings(Model model) {
        model.addAttribute("pendingMvFunds", pendingMvFundService.findAllAttente());
        return "back_office/transactions/index";
    }

    @GetMapping("/transactions/ok/{id}")
    public String validate(Model model,
                           @PathVariable int id) {
        String msg = "Validation OK";
        try {
            PendingMvFund pmf = pendingMvFundService.validate(id);
            msg += ". " + pendingMvFundService.sendEmail(pmf);
        } catch (PendingMvFundException pmfe) {
            msg = pmfe.getMessage();
        }
        model.addAttribute("msg", msg);
        return this.gotoListPendings(model);
    }

    @GetMapping("/transactions/no/{id}")
    public String refus(Model model,
                        @PathVariable int id) {
        String msg = "Refus OK";
        try {
            PendingMvFund pmf = pendingMvFundService.refus(id);
            msg += ". " + pendingMvFundService.sendEmail(pmf);
        } catch (PendingMvFundException pmfe) {
            msg = pmfe.getMessage();
        }
        model.addAttribute("msg", msg);
        return this.gotoListPendings(model);
    }

    @GetMapping("/transactions/add")
    public String gotoFormPending(Model model) {
        model.addAttribute("pendingMvFund", new PendingMvFund());
        model.addAttribute("accounts", pendingMvFundService.findAllAccounts());
        model.addAttribute("typeMvFunds", pendingMvFundService.findAllTypeMvFundsDepotRetrait());
        return "back_office/transactions/add";
    }

    @PostMapping("/transactions/save")
    public String save(Model model, @ModelAttribute PendingMvFund pendingMvFund) {
        pendingMvFund.setPendingState(pendingMvFundService.getEtatAttente());
        pendingMvFundService.save(pendingMvFund);

        model.addAttribute("msg", pendingMvFundService.sendEmail(pendingMvFund));
        return gotoFormPending(model);
    }
}
