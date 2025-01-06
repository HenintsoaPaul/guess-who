package itu.crypto.controller;

import itu.crypto.dto.ApiResponse;
import itu.crypto.dto.AuthDTO;
import itu.crypto.service.AuthService;
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
    private final AuthService authService;

    @GetMapping
    public String goToFirstForm(Model model) {
	model.addAttribute("registerDTO", new AuthDTO());
	return "register/index";
    }

    @PostMapping("/auth")
    public String authenticateFirstForm(Model model, @ModelAttribute("registerDTO") AuthDTO registerDTO) {
	ApiResponse apiResponse = authService.sendData(registerDTO);

	if (apiResponse.isOk()) {
	    // goto pin form
	    //	    model.addAttribute("dto", new AuthDTO(registerDTO.getEmail()));
	    System.out.println("dto: " + registerDTO);
	    model.addAttribute("dto", registerDTO);
	    model.addAttribute("msg", apiResponse.getMessage());
	    System.out.println(apiResponse.getData());
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
    public String authenticatePinForm(Model model, @ModelAttribute("registerDTO") AuthDTO registerDTO) {
	ApiResponse apiResponse = authService.sendPin(registerDTO);

	if (apiResponse.isOk()) {
	    // get token from apiResponse
	    System.out.println("msg: " + apiResponse.getMessage());
	    System.out.println("data: " + apiResponse.getData());

	    // save it in the Session

	    // goto home page
	    return "redirect:/index";
	} else {
	    // goto pin form
	    model.addAttribute("msg", apiResponse.getMessage());
	    model.addAttribute("errors", apiResponse.getErrors());
	    model.addAttribute("dto", registerDTO);
	    return "register/pin";
	}
    }
}
