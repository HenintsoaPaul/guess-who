package itu.crypto.service;

import itu.crypto.entity.cours.Cours;
import itu.crypto.entity.Crypto;
import itu.crypto.repository.CryptoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoService {
    private final CryptoRepository cryptoRepository;

    public List<Crypto> findAll() {
        return cryptoRepository.findAll();
    }

    public Crypto findById(Integer idCrypto) throws Exception {
        return cryptoRepository.findById(idCrypto).orElseThrow(() -> new Exception("Crypto not found"));
    }

    public double avg(List<Cours> cryptos) {
        return cryptos.stream()
                .mapToDouble(Cours::getPu)
                .average()
                .orElse(0.0);
    }
}
