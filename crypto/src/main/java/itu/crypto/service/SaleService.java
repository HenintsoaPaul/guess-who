package itu.crypto.service;

import itu.crypto.entity.Sale;
import itu.crypto.entity.SaleDetail;
import itu.crypto.repository.SaleDetailRepository;
import itu.crypto.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final SaleRepository saleRepository;
    private final SaleDetailRepository saleDetailRepository;

    public Sale findById(int id) {
	return saleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Sale not found"));
    }

    public List<Sale> findAllByIdAccount(Integer idAccount) {
	return saleRepository.findAllByIdAccount(idAccount);
    }

    public List<SaleDetail> findAllSaleDetails(Sale sale) {
	return saleDetailRepository.findAllBySale(sale);
    }
}
