package itu.crypto.controller;

import itu.crypto.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final LoginService loginService;

    @GetMapping
    public String goToHome(Model model) {
	return "index";
    }
}
