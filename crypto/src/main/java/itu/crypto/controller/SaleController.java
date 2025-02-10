package itu.crypto.controller;

import itu.crypto.dto.SaleFormData;
import itu.crypto.entity.account.Account;
import itu.crypto.entity.sale.Sale;
import itu.crypto.entity.sale.SaleDetail;
import itu.crypto.repository.CryptoRepository;
import itu.crypto.service.account.AccountService;
import itu.crypto.service.SaleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sales")
public class SaleController {
    private final SaleService saleService;
    private final AccountService accountService;
    private final CryptoRepository cryptoRepository;

    @GetMapping
    public String goToList(Model model, HttpSession session) {
        Integer idAccount = (Integer) session.getAttribute("id_account");
        List<Sale> mySales = saleService.findAllByIdAccount(idAccount);

        model.addAttribute("sales", mySales);
        return "sales/index";
    }

    @GetMapping("/{id}")
    public String goToDetail(Model model, @PathVariable Integer id) {
        Sale sale = saleService.findById(id);
        List<SaleDetail> saleDetails = saleService.findAllSaleDetails(sale);

        model.addAttribute("sale", sale);
        model.addAttribute("saleDetails", saleDetails);
        return "sales/detail";
    }

    @GetMapping("/add")
    public String goToForm(Model model, HttpSession session) {
        Integer idAccount = (Integer) session.getAttribute("id_account");
        Account myAccount = accountService.findById(idAccount).orElseThrow();

        model.addAttribute("saleFormData", new SaleFormData(myAccount));
        model.addAttribute("cryptoCurrencies", cryptoRepository.findAll());
        return "sales/add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("saleFormData") SaleFormData saleFormData) throws Exception {
        saleService.save(saleFormData);
        return "redirect:/sales";
    }
}
