package itu.crypto.controller.transactions;

import itu.crypto.entity.fund.PendingMvFundException;
import itu.crypto.service.transaction.fund.PendingMvFundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
