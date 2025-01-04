package itu.crypto.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");  // Recherche index.jsp dans /WEB-INF/jsp/
    }
}
