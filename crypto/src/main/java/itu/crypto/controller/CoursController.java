package itu.crypto.controller;

import itu.crypto.entity.Cours;
import itu.crypto.enums.CoursAnalysisType;
import itu.crypto.service.CoursService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cours")
public class CoursController {

    private final CoursService coursService;

    @GetMapping
    public String goToList(Model model,
                           @RequestParam(required = false, name = "typeAnalyse") CoursAnalysisType analysisType,
                           @RequestParam(required = false) Integer idCrypto,
                           @RequestParam(required = false) String dateMin,
                           @RequestParam(required = false) String dateMax) {

        List<Cours> cours = coursService.findAllByDateInInterval(dateMin, dateMax);

        if (idCrypto != null && idCrypto != -1) {
            cours = cours.stream()
                    .filter(cp -> cp.getCrypto().getId().equals(idCrypto))
                    .toList();
        }

        if (analysisType == null) {
            analysisType = CoursAnalysisType.MAX_COURS;
            model.addAttribute("analyses", cours);
        } else {
            List<Cours> analyses = coursService.getAnalysis(analysisType, cours);
            model.addAttribute("analyses", analyses);
        }


        model.addAttribute("cryptos", coursService.findAllCrypto());
        model.addAttribute("analyseTypes", CoursAnalysisType.values());
        model.addAttribute("analyseType", analysisType);
        return "cours/index";
    }
}
