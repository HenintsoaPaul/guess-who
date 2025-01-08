package itu.crypto.controller;

import itu.crypto.dto.ApiResponse;
import itu.crypto.dto.login.LoginRequest;
import itu.crypto.dto.login.LoginResponse;
import itu.crypto.service.LoginService;
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
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;

    @GetMapping
    public String goToFirstForm(Model model) {
	model.addAttribute("loginRequest", new LoginRequest());
	return "login/index";
    }

    @PostMapping("/auth")
    public String authenticateFirstForm(Model model, @ModelAttribute("loginRequest") LoginRequest loginRequest) {
	ApiResponse apiResponse = loginService.sendLoginDto(loginRequest);

	if (apiResponse.isOk()) {
	    // goto pin form
	    model.addAttribute("dto", loginRequest);
	    model.addAttribute("msg", apiResponse.getMessage());

	    // todo: fafana rehefa mande ilay email
	    System.out.println(apiResponse.getData());
	    // todo: fafana rehefa mande ilay email

	    return "login/pin";
	} else {
	    // back to email form
	    model.addAttribute("msg", apiResponse.getMessage());
	    model.addAttribute("loginRequest", new LoginRequest());
	    return "login/index";
	}
    }

    @PostMapping("/pin/auth")
    public String authenticatePinForm(Model model, @ModelAttribute("loginRequest") LoginRequest loginRequest, HttpSession session) {
	ApiResponse apiResponse = loginService.sendPin(loginRequest);

	if (apiResponse.isOk()) {
	    // get token from apiResponse
	    System.out.println("msg: " + apiResponse.getMessage());
	    System.out.println("data: " + apiResponse.getData());

	    // save it in the Session
	    LoginResponse loginResponse = new LoginResponse(apiResponse);
	    System.out.println("loginResponse: " + loginResponse);
	    String token = loginResponse.getToken();
	    session.setAttribute("token", token);
	    session.setAttribute("token_expiration", loginResponse.getExpiration());

	    // goto home page
	    return "index";
	} else {
	    // back to pin form
	    model.addAttribute("msg", apiResponse.getMessage());
	    model.addAttribute("dto", loginRequest);
	    return "login/pin";
	}
    }
}