package itu.crypto.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import itu.crypto.dto.WalletDTO;
import itu.crypto.service.WalletService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @GetMapping("/wallets/{id}")
    public ResponseEntity<List<WalletDTO>> getWalletByUser(@PathVariable int id) {
        List<WalletDTO> wallets = walletService.getWalletByUser(id);
        return ResponseEntity.ok(wallets);
    }

}
