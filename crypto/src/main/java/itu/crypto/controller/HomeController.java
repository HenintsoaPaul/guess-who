package itu.crypto.controller;

import itu.crypto.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @GetMapping
    public String goToHome(Model model) {
	return "index";
    }
}
