package itu.crypto.controller;

import itu.crypto.service.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conversion")
public class ConversionController {

    private final ConversionService conversionService;

    @Autowired
    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @GetMapping("/crypto-to-dollar")
    public double convertCryptoToDollar(@RequestParam String symbol, @RequestParam double amountCrypto) {
        return conversionService.convertCryptoToDollar(symbol, amountCrypto);
    }

    @GetMapping("/dollar-to-crypto")
    public double convertDollarToCrypto(@RequestParam String symbol, @RequestParam double amountDollar) {
        return conversionService.convertDollarToCrypto(symbol, amountDollar);
    }
}
