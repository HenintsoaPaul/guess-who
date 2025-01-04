package itu.crypto.service;

import itu.crypto.entity.CryptoCurrency;
import itu.crypto.repository.CryptoCurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoCurrencyService {
    private final CryptoCurrencyRepository cryptoCurrencyRepository;
}
