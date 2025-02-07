package itu.crypto.controller.transactions;

import itu.crypto.entity.fund.PendingMvFund;
import itu.crypto.entity.fund.PendingMvFundException;
import itu.crypto.service.transaction.fund.PendingMvFundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/transactions/fund/pending")
public class PendingMvFundController {

    private final PendingMvFundService pendingMvFundService;

    @GetMapping
    public String gotoListPendings(Model model) {
        model.addAttribute("pendingMvFund", pendingMvFundService.findAllAttente());
        return "transactions/fund/pending";
    }

    @GetMapping("/ok/{id}")
    public String validate(Model model,
                           @PathVariable int id) {
        String msg = "Validation OK";
        try {
            pendingMvFundService.validate(id);
        } catch (PendingMvFundException pmfe) {
            msg = pmfe.getMessage();
        }
        model.addAttribute("msg", msg);
        return this.gotoListPendings(model);
    }

    @GetMapping("/no/{id}")
    public String refus(Model model,
                        @PathVariable int id) {
        String msg = "Refus OK";
        try {
            pendingMvFundService.refus(id);
        } catch (PendingMvFundException pmfe) {
            msg = pmfe.getMessage();
        }
        model.addAttribute("msg", msg);
        return this.gotoListPendings(model);
    }

    @GetMapping("/add")
    public String gotoFormPending(Model model) {
        model.addAttribute("pendingMvFund", new PendingMvFund());
        model.addAttribute("accounts", pendingMvFundService.findAllAccounts());
        model.addAttribute("typeMvFunds", pendingMvFundService.findAllTypeMvFundsDepotRetrait());
        return "transactions/fund/pending/add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute PendingMvFund pendingMvFund) {
        pendingMvFund.setPendingState(pendingMvFundService.getEtatAttente());
        pendingMvFundService.save(pendingMvFund);
        return "redirect:/transactions/fund/pending";
    }
}
