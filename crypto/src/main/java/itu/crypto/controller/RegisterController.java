package itu.crypto.controller;

import itu.crypto.dto.ApiResponse;
import itu.crypto.dto.register.RegisterRequest;
import itu.crypto.service.RegisterService;
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
    public String authenticateFirstForm(Model model, @ModelAttribute("registerRequest") RegisterRequest registerRequest) {
	ApiResponse apiResponse = registerService.sendData(registerRequest);

	if (apiResponse.isOk()) {
	    // goto pin form

	    System.out.println("dto: " + registerRequest);

	    model.addAttribute("dto", registerRequest);
	    model.addAttribute("msg", apiResponse.getMessage());
	    System.out.println("ApiResponse: " + apiResponse.getData());

	    return "register/pin";
	} else {
	    // goto email form
	    System.out.println("errors: " + apiResponse.getErrors());
	    System.out.println("msg: " + apiResponse.getMessage());

	    model.addAttribute("msg", apiResponse.getMessage());
	    model.addAttribute("errors", apiResponse.getErrors());
	    return "redirect:/register/index";
	}
    }

    @PostMapping("/pin/auth")
    public String authenticatePinForm(Model model, @ModelAttribute("registerRequest") RegisterRequest registerRequest) {
	ApiResponse apiResponse = registerService.sendPin(registerRequest);

	if (apiResponse.isOk()) {
	    System.out.println("dto: " + registerRequest);
	    registerService.register(registerRequest);

	    // Get id_account as Response
	    // Desearilize data...

	    // Generate token for the id_account
	    // use rest_template ...

	    // Add token to the Session
	    // make a custom function...

	    return "redirect:/index";
	} else {
	    // goto pin form
	    model.addAttribute("msg", apiResponse.getMessage());
	    model.addAttribute("errors", apiResponse.getErrors());
	    model.addAttribute("dto", registerRequest);
	    return "register/pin";
	}
    }
}
