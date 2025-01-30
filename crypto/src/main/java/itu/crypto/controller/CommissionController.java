package itu.crypto.controller;

import itu.crypto.entity.commission.CommissionRate;
import itu.crypto.service.transaction.CommissionRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/commissions")
public class CommissionController {
    private final CommissionRateService commissionRateService;

    @GetMapping
    public String goToList(Model model) {
        model.addAttribute("commissions", commissionRateService.findAll());
        model.addAttribute("currentCommissions", commissionRateService.findCurrentCommissions());
        return "commissions/index";
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
