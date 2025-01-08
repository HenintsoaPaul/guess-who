package itu.crypto.service;

import itu.crypto.entity.Sale;
import itu.crypto.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final SaleRepository saleRepository;

    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    public List<Sale> findAllByIdAccount(Integer idAccount) {
        return saleRepository.findAllByIdAccount(idAccount);
    }
}
