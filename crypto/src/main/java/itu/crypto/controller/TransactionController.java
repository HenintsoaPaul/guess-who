package itu.crypto.controller;

import itu.crypto.entity.Transaction;
import itu.crypto.repository.CryptoCurrencyRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import itu.crypto.service.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final CoursService coursService;
    private final CryptoCurrencyRepository cryptoCurrencyRepository;

    @GetMapping
    public String getAll(Model model, HttpSession session) {
	// authenticate user

	model.addAttribute("transactions", transactionService.findAll());
	return "transactions/index";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable Integer id) throws Exception {
	// authenticate user

	Transaction u = transactionService.findById(id);
	model.addAttribute("transaction", u);
	return "transactions/detail";
    }

    @GetMapping("/add")
    public String gotoSave(Model model) {
	// fund actuel
	// list crypto
	// wallet
	// cours

	model.addAttribute("cours", coursService.findCurrentCours());
	model.addAttribute("cryptoCurrencies", cryptoCurrencyRepository.findAll());
	model.addAttribute("wallet", coursService.findCurrentCours());
	model.addAttribute("fund", coursService.findCurrentCours());

	// depot -- retrait
	model.addAttribute("transaction", new Transaction());

	return "transactions/add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("transaction") Transaction transaction) {
	transactionService.save(transaction);
	return "redirect:/transactions";
    }
}
