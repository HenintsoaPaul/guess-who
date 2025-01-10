package itu.crypto.controller;

import itu.crypto.service.LoginService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itu.crypto.entity.*;
import itu.crypto.repository.*;

import java.util.List;
@Controller
@RequestMapping("/crypto")
public class ChartController {
    private final WalletService walletService;

    @Autowired
    public TableauController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/historique")
    public List<TableauDTO> getHistoriqueAllUsers() {
        return walletService.getHistoriqueAllUsers();
    }
}