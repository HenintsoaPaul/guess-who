package itu.crypto.controller;

import itu.crypto.entity.cours.Cours;
import itu.crypto.service.CoursService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cours")
@RequiredArgsConstructor
public class ApiCoursController {
    private final CoursService coursService;

    @GetMapping("/generate")
    public List<Cours> generateCours() {
        return coursService.generateCours();
    }
}
