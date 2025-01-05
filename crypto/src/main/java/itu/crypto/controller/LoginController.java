package itu.crypto.controller;

import itu.crypto.dto.ApiResponse;
import itu.crypto.dto.login.LoginDTO;
import itu.crypto.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;

    @GetMapping
    public String goToFirstForm(Model model) {
	model.addAttribute("loginDTO", new LoginDTO());
	return "login/index";
    }

    @PostMapping("/auth")
    public String authenticateFirstForm(Model model, @ModelAttribute("loginDTO") LoginDTO loginDTO) {
	ApiResponse apiResponse = loginService.sendLoginDto(loginDTO);

	if (apiResponse.isOk()) {
	    // goto pin form
	    //	    model.addAttribute("dto", new LoginDTO(loginDTO.getEmail()));
	    System.out.println("dto: " + loginDTO);
	    model.addAttribute("dto", loginDTO);
	    model.addAttribute("msg", apiResponse.getMessage());
	    System.out.println(apiResponse.getData());
	    return "login/pin";
	} else {
	    // goto email form
	    System.out.println("errors: " + apiResponse.getErrors());
	    System.out.println("msg: " + apiResponse.getMessage());

	    model.addAttribute("msg", apiResponse.getMessage());
	    model.addAttribute("errors", apiResponse.getErrors());
	    return "redirect:/login/index";
	}
    }

    @PostMapping("/pin/auth")
    public String authenticatePinForm(Model model, @ModelAttribute("loginDTO") LoginDTO loginDTO) {
	ApiResponse apiResponse = loginService.sendPin(loginDTO);

	if (apiResponse.isOk()) {
	    // goto home page
	    return "redirect:/index";
	} else {
	    // goto pin form
	    model.addAttribute("msg", apiResponse.getMessage());
	    model.addAttribute("errors", apiResponse.getErrors());
	    model.addAttribute("dto", loginDTO);
	    return "login/pin";
	}
    }
}
