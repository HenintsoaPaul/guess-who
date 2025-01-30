package itu.crypto.controller;

import itu.crypto.dto.ApiResponse;
import itu.crypto.dto.register.RegisterResponse;
import itu.crypto.dto.register.RegisterRequest;
import itu.crypto.service.account.RegisterService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {
    private final RegisterService registerService;

    @GetMapping
    public String goToFirstForm(Model model) {
	model.addAttribute("registerRequest", new RegisterRequest());
	return "register/index";
    }

    @PostMapping("/auth")
    public String authenticateFirstForm(Model model,
	    @ModelAttribute("registerRequest") RegisterRequest registerRequest) {
	ApiResponse apiResponse = registerService.sendData(registerRequest);

	if (apiResponse.isOk()) {
	    // goto pin form
	    model.addAttribute("dto", registerRequest);
	    model.addAttribute("msg", apiResponse.getMessage());
	    System.out.println("ApiResponse: " + apiResponse.getData());

	    return "register/pin";
	} else {
	    // goto email form
	    System.out.println("msg: " + apiResponse.getMessage());

	    model.addAttribute("msg", apiResponse.getMessage());
	    model.addAttribute("registerRequest", new RegisterRequest());
	    return "register/index";
	}
    }

    @PostMapping("/pin/auth")
    public String authenticatePinForm(HttpSession session, Model model,
	    @ModelAttribute("registerRequest") RegisterRequest registerRequest) {
	ApiResponse apiResponse = registerService.sendPin(registerRequest);

	if (apiResponse.isOk()) {
	    System.out.println("dto: " + registerRequest);
	    registerService.register(registerRequest);

	    // Add token from API into the Session
	    System.out.println("apiResponse: " + apiResponse.getData());

	    RegisterResponse registerResponse = new RegisterResponse(apiResponse);
	    System.out.println("registerResponse: " + registerResponse);
	    session.setAttribute("token", registerResponse.getToken());
	    session.setAttribute("token_expiration", registerResponse.getExpiration());

	    return "index";
	} else {
	    // goto pin form
	    model.addAttribute("msg", apiResponse.getMessage());
	    model.addAttribute("errors", apiResponse.getErrors());
	    model.addAttribute("dto", registerRequest);
	    return "register/pin";
	}
    }
}
