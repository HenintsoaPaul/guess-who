package itu.crypto.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/front-office")
public class FrontOfficeController {
    @GetMapping
    public String goToSample(Model model) {
	return "front_office/index";
    }
}
