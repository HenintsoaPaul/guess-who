package itu.crypto.controller;

import itu.crypto.service.transaction.fund.PendingMvFundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/transactions/mv-fund")
public class PendingMvFundController {

    private final PendingMvFundService pendingMvFundService;

    @GetMapping
    public String gotoListPendings(Model model) {
        model.addAttribute("pendingMvFunds", pendingMvFundService.findAllAttente());

        return "transactions/mv-fund/pending";
    }
}
