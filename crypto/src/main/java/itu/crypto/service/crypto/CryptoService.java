package itu.crypto.service.crypto;

import itu.crypto.entity.cours.Cours;
import itu.crypto.entity.crypto.Crypto;
import itu.crypto.firebase.firestore.generalisation.BaseService;
import itu.crypto.repository.CryptoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CryptoService implements BaseService<Crypto> {
    private final CryptoRepository cryptoRepository;

    public List<Crypto> findAll() {
        return cryptoRepository.findAll();
    }

    @Override
    public Optional<Crypto> findById(int id) {
        return cryptoRepository.findById(id);
    }

    public double avg(List<Cours> cryptos) {
        return cryptos.stream()
                .mapToDouble(Cours::getPu)
                .average()
                .orElse(0.0);
    }
}
