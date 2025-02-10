package itu.crypto.controller;

import itu.crypto.api.ApiResponse;
import itu.crypto.dto.login.LoginRequest;
import itu.crypto.dto.login.LoginResponse;
import itu.crypto.entity.account.Account;
import itu.crypto.entity.account.Admin;
import itu.crypto.service.account.LoginService;
import itu.crypto.service.SessionService;
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
    private final SessionService sessionService;

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        sessionService.viderSession(session);

        LoginRequest loginRequest = new LoginRequest("rocruxappafra-4143@yopmail.com", "mypassword");
        model.addAttribute("loginRequest", loginRequest);
        return "login/index";
    }

    @GetMapping
    public String goToFirstForm(HttpSession session, Model model) {
        if (session.getAttribute("loginError") != null) {
            model.addAttribute("msg", session.getAttribute("loginError"));
            session.removeAttribute("loginError");
        }

        LoginRequest loginRequest = new LoginRequest("rocruxappafra-4143@yopmail.com", "mypassword");
        model.addAttribute("loginRequest", loginRequest);
        return "login/index";
    }

    @PostMapping("/auth")
    public String authenticateFirstForm(Model model,
                                        @ModelAttribute("loginRequest") LoginRequest loginRequest) {
        ApiResponse apiResponse = loginService.sendLoginDto(loginRequest);

        if (apiResponse.isOk()) {
            // goto pin form
            model.addAttribute("dto", loginRequest);
            model.addAttribute("msg", apiResponse.getMessage());

            return "login/pin";
        } else {
            // back to email form
            model.addAttribute("msg", apiResponse.getMessage());
            model.addAttribute("loginRequest", new LoginRequest());
            return "login/index";
        }
    }

    @PostMapping("/pin/auth")
    public String authenticatePinForm(Model model,
                                      @ModelAttribute("loginRequest") LoginRequest loginRequest,
                                      HttpSession session) {
        ApiResponse apiResponse = loginService.sendPin(loginRequest);

        if (apiResponse.isOk()) {
            // Get user by email, then save in Session
            Account myAccount = loginService.getAccount(loginRequest);

            // Get user admin status
            Admin admin = loginService.getAdminStatus(myAccount);

            // save it in the Session
            LoginResponse loginResponse = new LoginResponse(apiResponse);
            String token = loginResponse.getToken();
            String tokenExpiration = loginResponse.getExpiration();

            // Init new Session
            sessionService.viderSession(session);
            sessionService.initSession(session, myAccount.getId(), token, tokenExpiration, admin);

            // goto home page
            if (admin == null) {
                return "redirect:/front-office";
            } else {
                return "redirect:/back-office";
            }
        } else {
            // back to pin form
            model.addAttribute("msg", apiResponse.getMessage());
            model.addAttribute("dto", loginRequest);
            return "login/pin";
        }
    }
}
