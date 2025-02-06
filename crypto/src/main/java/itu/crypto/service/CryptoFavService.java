package itu.crypto.service;

import itu.crypto.entity.crypto.CryptoFav;
import itu.crypto.firebase.firestore.generalisation.BaseService;
import itu.crypto.repository.CryptoFavRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoFavService implements BaseService<CryptoFav> {

    private final CryptoFavRepository cryptoFavRepository;

    public List<CryptoFav> findAll() {
        return cryptoFavRepository.findAll();
    }
}
