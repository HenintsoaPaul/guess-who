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
        model.addAttribute("pendingMvFunds", pendingMvFundService.findAllAttente());
        return "transactions/fund/pending";
    }

    @GetMapping("/ok/{id}")
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

    @GetMapping("/no/{id}")
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

    @GetMapping("/add")
    public String gotoFormPending(Model model) {
        model.addAttribute("pendingMvFund", new PendingMvFund());
        model.addAttribute("accounts", pendingMvFundService.findAllAccounts());
        model.addAttribute("typeMvFunds", pendingMvFundService.findAllTypeMvFundsDepotRetrait());
        return "transactions/fund/pending/add";
    }

    @PostMapping("/save")
    public String save(Model model, @ModelAttribute PendingMvFund pendingMvFund) {
        pendingMvFund.setPendingState(pendingMvFundService.getEtatAttente());
        pendingMvFundService.save(pendingMvFund);

        model.addAttribute("msg", pendingMvFundService.sendEmail(pendingMvFund));
        return gotoFormPending(model);
    }
}
