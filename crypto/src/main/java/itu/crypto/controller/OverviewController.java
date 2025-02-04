package itu.crypto.controller;

import itu.crypto.service.account.AccountOverviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
@RequestMapping("/overview")
public class OverviewController {
    private final AccountOverviewService accountOverviewService;

    @GetMapping
    public String getAll(Model model,
                         @RequestParam(required = false) String dateMin,
                         @RequestParam(required = false) String dateMax) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime dmin = (dateMin == null || dateMin.isEmpty()) ? null : LocalDateTime.parse(dateMin + " 00:00:00", formatter),
                dmax = (dateMax == null || dateMax.isEmpty()) ? null : LocalDateTime.parse(dateMax + " 23:59:59", formatter);

        model.addAttribute("overviews", accountOverviewService.findAllAccountOverview(dmin, dmax));
        return "overview/index";
    }
}
