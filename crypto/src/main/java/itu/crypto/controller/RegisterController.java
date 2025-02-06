package itu.crypto.controller;

import itu.crypto.api.ApiResponse;
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

    // Pour la validation par PIN apres la redirection automatique
    @PostMapping("/auth")
    public String authenticateFirstForm(Model model,
                                        @ModelAttribute("registerRequest") RegisterRequest registerRequest) {
        ApiResponse apiResponse = registerService.sendData(registerRequest);

        if (apiResponse.isOk()) {
            // goto pin form
            model.addAttribute("dto", registerRequest);
            model.addAttribute("msg", apiResponse.getMessage());
            model.addAttribute("registerRequest", registerRequest);

            return "register/pin";
        } else {
            // goto email form
            model.addAttribute("msg", apiResponse.getMessage());
            model.addAttribute("registerRequest", new RegisterRequest());

            return "register/index";
        }
    }

    // Pour les validations par PIN en attentes
    @GetMapping("/pending")
    public String gotoPinForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register/pin";
    }

    @PostMapping("/pin/auth")
    public String authenticatePinForm(HttpSession session, Model model,
                                      @ModelAttribute("registerRequest") RegisterRequest registerRequest) {
        // Save account to identity
        ApiResponse apiResponse = registerService.sendPin(registerRequest);

        if (apiResponse.isOk()) {
            RegisterResponse registerResponse = new RegisterResponse(apiResponse);

            // Save account to crypto
            registerService.register(registerRequest, registerResponse);

            // Add token from API into the Session
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
