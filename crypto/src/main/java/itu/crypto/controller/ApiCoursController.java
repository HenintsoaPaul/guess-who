package itu.crypto.controller;

import itu.crypto.dto.CryptoValueDTO;
import itu.crypto.entity.cours.Cours;
import itu.crypto.entity.crypto.Crypto;
import itu.crypto.service.CoursService;
import itu.crypto.service.crypto.CryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cours")
@RequiredArgsConstructor
public class ApiCoursController {
    private final CoursService coursService;
    private final CryptoService cryptoPriceService;

    @GetMapping("/generate")
    public List<Cours> generateCours() {
        return coursService.generateCours();
    }

    @GetMapping("/crypto-values")
    public List<CryptoValueDTO> getCryptoValues(@RequestParam Integer cryptoId) {
        List<Cours> coursList = coursService.findAllByCryptoId(cryptoId);
        return coursList.stream()
                .map(cours -> new CryptoValueDTO(cours.getCrypto().getName(), cours.getDateCours(), cours.getPu()))
                .collect(Collectors.toList());
    }

    @GetMapping("/cryptos")
    public List<Crypto> getCryptos() {
        return cryptoPriceService.findAll();
    }
}
