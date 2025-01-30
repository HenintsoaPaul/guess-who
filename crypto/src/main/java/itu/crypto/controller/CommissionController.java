package itu.crypto.controller;

import itu.crypto.entity.commission.Commission;
import itu.crypto.service.transaction.CommissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/commissions")
public class CommissionController {
    private final CommissionService commissionService;

    @GetMapping
    public String goToList(Model model) {
        model.addAttribute("commissions", commissionService.findAll());
        model.addAttribute("currentCommissions", commissionService.findCurrentCommissions());
        return "commissions/index";
    }

    @GetMapping("/edit")
    public String goToForm(Model model) {
        model.addAttribute("commission", new Commission());
        model.addAttribute("commissionTypes", commissionService.findAllTypes());
        return "commissions/edit";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("commission") Commission commission) throws Exception {
        commissionService.save(commission);
        return "redirect:/commissions/edit";
    }
}
