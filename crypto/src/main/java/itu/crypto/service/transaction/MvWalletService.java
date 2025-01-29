package itu.crypto.service.transaction;

import itu.crypto.entity.MvWallet;
import itu.crypto.repository.transaction.MvWalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MvWalletService {
    private final MvWalletRepository mvWalletRepository;

    public List<MvWallet> findAll() {
        return mvWalletRepository.findAll();
    }

    public MvWallet findById(Integer id) throws Exception {
        return mvWalletRepository.findById(id).orElseThrow(() -> new Exception("MvWallet not found"));
    }

    public List<MvWallet> findAllAfterDateMin(LocalDate dateMin) {
        return mvWalletRepository.findAllAfterDateMin(dateMin);
    }

    public List<MvWallet> findAllBeforeDateMax(LocalDate dateMax) {
        return mvWalletRepository.findAllBeforeDateMax(dateMax);
    }
}
