package itu.crypto.controller;

import itu.crypto.dto.SaleFormData;
import itu.crypto.entity.Account;
import itu.crypto.entity.Sale;
import itu.crypto.entity.SaleDetail;
import itu.crypto.repository.CommissionRepository;
import itu.crypto.repository.CryptoRepository;
import itu.crypto.service.AccountService;
import itu.crypto.service.SaleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/commissions")
public class CommissionController {
    private final CommissionRepository commissionRepository;

    @GetMapping
    public String goToList(Model model) {
	model.addAttribute("commissions", commissionRepository.findAll());
	return "commissions/index";
    }

//    @GetMapping("/{id}")
//    public String goToUpdate(Model model, @PathVariable Integer id) {
////	Sale sale = saleService.findById(id);
////	List<SaleDetail> saleDetails = saleService.findAllSaleDetails(sale);
//
//
////	model.addAttribute("", sale);
////	model.addAttribute("saleDetails", saleDetails);
//
//	return "commissions/detail";
//    }

//
//    @GetMapping("/add")
//    public String goToForm(Model model, HttpSession session) {
//	Integer idAccount = (Integer) session.getAttribute("id_account");
//	Account myAccount = accountService.findById(idAccount);
//
//	model.addAttribute("saleFormData", new SaleFormData(myAccount));
//	model.addAttribute("cryptoCurrencies", cryptoRepository.findAll());
//	return "sales/add";
//    }

//    @PostMapping("/save")
//    public String save(@ModelAttribute("saleFormData") SaleFormData saleFormData) throws Exception {
//	saleService.save(saleFormData);
//	return "redirect:/sales";
//    }
}
