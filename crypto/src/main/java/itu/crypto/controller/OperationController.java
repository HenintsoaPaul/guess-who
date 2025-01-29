package itu.crypto.controller;

import itu.crypto.entity.MvWallet;
import itu.crypto.service.transaction.MvWalletService;
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
                         @RequestParam(required = false) String dateMin,
                         @RequestParam(required = false) String dateMax) {
        List<MvWallet> mvWallets = mvWalletService.findAll();

        LocalDate dd;
        if (dateMin != null && !dateMin.isEmpty()) {
            dd = LocalDate.parse(dateMin, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            mvWallets.retainAll(mvWalletService.findAllAfterDateMin(dd));
        }
        if (dateMax != null && !dateMax.isEmpty()) {
            dd = LocalDate.parse(dateMax, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            mvWallets.retainAll(mvWalletService.findAllBeforeDateMax(dd));
        }

        model.addAttribute("mvWallets", mvWallets);
        return "operations/index";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable Integer id) throws Exception {
        model.addAttribute("mvWallet", mvWalletService.findById(id));
        return "operations/by-user";
    }
}
