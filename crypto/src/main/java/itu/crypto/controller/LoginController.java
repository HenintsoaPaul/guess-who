package itu.crypto.controller;

import itu.crypto.dto.ApiResponse;
import itu.crypto.dto.login.LoginDTO;
import itu.crypto.entity.Account;
import itu.crypto.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;

    @GetMapping
    public String goToFirstForm(Model model) {
	model.addAttribute("account", new Account());
	return "login/index";
    }

    @GetMapping("/auth")
    public String authenticateFirstForm(Model model, @ModelAttribute("account") Account account) {
	// send object to laravel
	LoginDTO payload = new LoginDTO(account.getEmail(), account.getPassword());

	ApiResponse apiResponse = loginService.sendLoginDto(payload);
	if (apiResponse.isOk()) {
	    return "redirect:/login/pin";
	} else {
	    // Retrieve remaining attempts from the API call and display them in the form
	    System.out.println(apiResponse.getErrors());
	    model.addAttribute("error", apiResponse.getMessage());
	    return "login/index";
	}
    }

    @GetMapping("/pin")
    public String goToPinForm(Model model, @ModelAttribute("account") Account account) {
	model.addAttribute("account", new Account());
	return "login/pin";
    }

    @GetMapping("/pin/auth")
    public String authenticatePinForm(Model model, @ModelAttribute("account") Account account) {
	model.addAttribute("account", new Account());
	return "login/index";
    }
}
