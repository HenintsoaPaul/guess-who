package itu.crypto.controller;

import itu.crypto.service.transaction.MvWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/operations")
public class OperationController {
//    private final TransactionService transactionService;
    private final MvWalletService mvWalletService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("mvWallets", mvWalletService.findAll());
        return "operations/index";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable Integer id) throws Exception {
        model.addAttribute("mvWallet", mvWalletService.findById(id));
//        model.addAttribute("transactionDetails", transactionDetailService.findAllByTransaction(trans));
        return "operations/by-user";
    }
}
