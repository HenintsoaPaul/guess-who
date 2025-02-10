package itu.crypto.controller;

import itu.crypto.entity.wallet.MvWallet;
import itu.crypto.service.transaction.wallet.MvWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/operations")
public class OperationController {
    private final MvWalletService mvWalletService;

    @GetMapping
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
        return "operations/index";
    }

    @GetMapping("/account/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        model.addAttribute("cryptos", mvWalletService.findAllCrypto());
        model.addAttribute("mvWallets", mvWalletService.findAllByIdAccount(id));
        model.addAttribute("idAccount", id);
        model.addAttribute("account", mvWalletService.findAccountById(id));
        return "operations/by-account";
    }

    @GetMapping("/account")
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

        return "operations/by-account";
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
}
