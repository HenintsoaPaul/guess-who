package itu.crypto.controller;

import com.google.api.Http;
import itu.crypto.entity.wallet.Wallet;
import itu.crypto.service.transaction.wallet.WalletService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import itu.crypto.dto.SaleFormData;
import itu.crypto.entity.sale.Sale;
import itu.crypto.entity.sale.SaleDetail;
import itu.crypto.entity.account.Account;
import itu.crypto.entity.cours.Cours;
import itu.crypto.entity.fund.PendingMvFund;
import itu.crypto.enums.CoursAnalysisType;
import itu.crypto.repository.CryptoRepository;
import itu.crypto.service.CoursService;
import itu.crypto.service.SaleService;
import itu.crypto.service.account.AccountService;
import itu.crypto.service.transaction.fund.PendingMvFundService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/front-office")
public class FrontOfficeController {

    private final CoursService coursService;
    private final SaleService saleService;
    private final AccountService accountService;
    private final CryptoRepository cryptoRepository;
    private final WalletService walletService;
    private final PendingMvFundService pendingMvFundService;

    // Home page
    @GetMapping
    public String goToSample(Model model, HttpSession session) {
        if (session.getAttribute("adminError") != null) {
            model.addAttribute("error", session.getAttribute("adminError"));
        }
        return "front_office/index";
    }

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
        return "front_office/cours";
    }

    // Graphe crypto
    @GetMapping("/crypto-graphe")
    public String goToCryptoGraphe(Model model) {
        return "/front_office/cryptoGraphe";
    }

    // Sales
    @GetMapping("/sales")
    public String goToList(Model model, HttpSession session) {
        Integer idAccount = (Integer) session.getAttribute("id_account");
        List<Sale> mySales = saleService.findAllByIdAccount(idAccount);

        model.addAttribute("sales", mySales);
        return "front_office/sales/index";
    }

    @GetMapping("/sales/{id}")
    public String goToDetail(Model model, @PathVariable Integer id) {
        Sale sale = saleService.findById(id);
        List<SaleDetail> saleDetails = saleService.findAllSaleDetails(sale);

        model.addAttribute("sale", sale);
        model.addAttribute("saleDetails", saleDetails);
        return "front_office/sales/detail";
    }

    @GetMapping("/sales/add")
    public String goToForm(Model model, HttpSession session) {
        Integer idAccount = (Integer) session.getAttribute("id_account");
        Account myAccount = accountService.findById(idAccount).orElseThrow();

        model.addAttribute("saleFormData", new SaleFormData(myAccount));
        model.addAttribute("cryptoCurrencies", cryptoRepository.findAll());
        return "front_office/sales/add";
    }

    @PostMapping("/sales/save")
    public String save(@ModelAttribute("saleFormData") SaleFormData saleFormData,
                       RedirectAttributes redirectAttributes) {
        try {
            saleService.save(saleFormData);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/front-office/sales/add";
        }
        return "redirect:/front-office/sales/id";
    }

    // Dépôt
    @GetMapping("/transaction/depotRetrait")
    public String goToDepot(Model model) {
        model.addAttribute("pendingMvFund", new PendingMvFund());
        model.addAttribute("accounts", pendingMvFundService.findAllAccounts());
        model.addAttribute("typeMvFunds", pendingMvFundService.findAllTypeMvFundsDepotRetrait());
        return "front_office/transaction/depot_retrait";
    }

    @PostMapping("/transaction/save")
    public String saveDepot(Model model, HttpSession session, @ModelAttribute PendingMvFund pendingMvFund) {
        Integer idAccount = (Integer) session.getAttribute("id_account");
        Account myAccount = accountService.findById(idAccount).orElseThrow();

        pendingMvFund.setPendingState(pendingMvFundService.getEtatAttente());
        pendingMvFund.setAccount(myAccount);
        pendingMvFundService.save(pendingMvFund);

        model.addAttribute("msg", "Nisy email lasa bro.");
        return goToHistorique(model, session);
    }

    @GetMapping("/transaction/historique")
    public String goToHistorique(Model model, HttpSession session) {
        Integer idAccount = (Integer) session.getAttribute("id_account");
        Account myAccount = accountService.findById(idAccount).orElseThrow();

        model.addAttribute("pendingMvFunds", myAccount);
        return "front_office/transaction/historique";
    }

    // Account
    @GetMapping("/account/profil")
    public String goToAccountProfil(Model model, HttpSession session) {
        Integer idAccount = (Integer) session.getAttribute("id_account");
        Account myAccount = accountService.findById(idAccount).orElseThrow();

        model.addAttribute("account", myAccount);
        return "front_office/profil";
    }

    //Wallet
    @GetMapping("/wallet")
    public String goToWallet(Model model, HttpSession session) {
        Integer idAccount = (Integer) session.getAttribute("id_account");
        Account myAccount = accountService.findById(idAccount).orElseThrow();

        List<Wallet> wallets = walletService.findAllByAccount(myAccount);
        int nbTotal = wallets.stream().mapToInt(Wallet::getQuantity).sum();

        model.addAttribute("wallets", walletService.findAllByAccount(myAccount));
        model.addAttribute("nbTotal", nbTotal);
        model.addAttribute("user", myAccount.getPseudo() + "(" + myAccount.getEmail() + ")");
        return "front_office/wallet/index";
    }

    // market
    @GetMapping("/market")
    public String goToMarket(Model model) {
        List<SaleDetail> saleDetails = saleService.findAllDispo();

        model.addAttribute("saleDetails", saleDetails);
        return "front_office/market/index";
    }

    @GetMapping("/market/{id}")
    public String goToMarketInfo(Model model, @PathVariable Integer id) {
        SaleDetail saleDetail = saleService.findByIdSaleDetail(id);
        Sale sale = saleService.findByIdSale(saleDetail.getSale().getId());
        Account accountSeller = accountService.findById(sale.getAccount().getId()).orElseThrow();

        model.addAttribute("saleDetail", saleDetail);
        model.addAttribute("accountSeller", accountSeller);
        return "front_office/market/buy";
    }

    @PostMapping("/market/buy")
    public String buyCrypto(@RequestParam("idSaleDetail") Integer idSaleDetail, @RequestParam("qtt") Integer quantity, RedirectAttributes redirectAttributes, HttpSession session) {
        try {
            // Integer idAccount = (Integer) session.getAttribute("id_account");
            // Account myAccount = accountService.findById(idAccount).orElseThrow();

            // todo: comment on production
            Account myAccount = accountService.findById(1).orElseThrow();

            SaleDetail saleDetail = saleService.findByIdSaleDetail(idSaleDetail);
            if (quantity > saleDetail.getQuantityLeft()) {
                throw new IllegalArgumentException("Quantité demandée supérieure à la quantité disponible.");
            }

            saleService.buyCrypto(myAccount, saleDetail, quantity);
            return "redirect:/front-office/market";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/front-office/market/" + idSaleDetail;
        }
    }

}
