package itu.crypto.controller;

import itu.crypto.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sales")
public class SaleController {
    private final SaleService saleService;

    @GetMapping
    public String goToList(Model model) {
	model.addAttribute("sales", saleService.findAll());
	return "sales/index";
    }
}
