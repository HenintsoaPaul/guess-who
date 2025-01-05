package itu.crypto.controller;

import itu.crypto.entity.Transaction;
import itu.crypto.repository.CryptoCurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import itu.crypto.service.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionDetailService transactionDetailService;
    private final CoursService coursService;
    private final CryptoCurrencyRepository cryptoCurrencyRepository;

    @GetMapping("/test")
    public String secureEndpoint() {
	return "You have accessed a secure endpoint!";
    }

    @GetMapping
    public String getAll(Model model) {
	model.addAttribute("transactions", transactionService.findAll());
	return "transactions/index";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable Integer id) throws Exception {
	Transaction trans = transactionService.findById(id);
	model.addAttribute("transaction", trans);
	model.addAttribute("transactionDetails", transactionDetailService.findAllByTransaction(trans));
	return "transactions/details";
    }

    //    @GetMapping("/add")
    //    public String gotoSave(Model model) {
    //	// fund actuel
    //	// list crypto
    //	// wallet
    //	// cours
    //
    //	model.addAttribute("cours", coursService.findCurrentCours());
    //	model.addAttribute("cryptoCurrencies", cryptoCurrencyRepository.findAll());
    //	model.addAttribute("wallet", coursService.findCurrentCours());
    //	model.addAttribute("fund", coursService.findCurrentCours());
    //
    //	// depot -- retrait
    //	model.addAttribute("transaction", new Transaction());
    //
    //	return "transactions/add";
    //    }
    //
    //    @PostMapping("/save")
    //    public String save(@ModelAttribute("transaction") Transaction transaction) {
    //	transactionService.save(transaction);
    //	return "redirect:/transactions";
    //    }
}
