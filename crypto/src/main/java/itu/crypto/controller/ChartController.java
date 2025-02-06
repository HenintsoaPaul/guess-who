package itu.crypto.controller;

import itu.crypto.entity.cours.Cours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import itu.crypto.entity.*;
import itu.crypto.repository.*;

import java.util.List;
@Controller
@RequestMapping("/crypto")
public class ChartController {

    @Autowired
    private CryptoRepository cryptoRepository; 

    @Autowired
    private CoursRepository coursRepository;

    // Endpoint pour récupérer la liste des cryptomonnaies
    @GetMapping("/all")
    public List<Crypto> getAllCryptos() {
        return cryptoRepository.findAll();
    }

    @GetMapping("/chart")
    public String redirectChart(Model model) {
        return "/chart/cours";
    }

    // Endpoint pour récupérer les cours d'une crypto donnée
    @GetMapping("/cours/{idCrypto}")
    public List<Cours> getCoursByCrypto(@PathVariable int idCrypto) {
        return coursRepository.findByIdCrypto(idCrypto);
    }
}

