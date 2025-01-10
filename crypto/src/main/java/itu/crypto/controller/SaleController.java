package itu.crypto.controller;

import itu.crypto.entity.Sale;
import itu.crypto.entity.SaleDetail;
import itu.crypto.service.SaleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sales")
public class SaleController {
    private final SaleService saleService;

    @GetMapping
    public String goToList(Model model, HttpSession session) {
	Integer idAccount = (Integer) session.getAttribute("id_account");
	List<Sale> mySales = saleService.findAllByIdAccount(idAccount);

	model.addAttribute("sales", mySales);
	return "sales/index";
    }

	@GetMapping("/dispo")
    public String goToListAchatDispo(Model model, HttpSession session) {
	Integer idAccount = (Integer) session.getAttribute("id_account");
	List<Sale> mySales = saleService.findAllSaleDetails(idAccount);

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
}
