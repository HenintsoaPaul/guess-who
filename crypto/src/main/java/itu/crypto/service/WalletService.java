package itu.crypto.service;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itu.crypto.dto.TableauDTO;
import itu.crypto.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.*;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;

    public TableauDTO mapToDTO(Object[] row) {
        if (row == null || row.length < 4) {
            throw new IllegalArgumentException("Invalid result set row.");
        }
        String user = (String) row[0];
        double totalAchat = ((BigDecimal) row[1]).doubleValue();
        double totalVente = ((BigDecimal) row[2]).doubleValue();
        double valeurPortefeuil = ((BigDecimal) row[3]).doubleValue();

        return new TableauDTO(user, totalAchat, totalVente, valeurPortefeuil);
    }

    public List<TableauDTO> mapToDTOList(List<Object[]> rows) {
        List<TableauDTO> dtos = new ArrayList<>();
        for (Object[] row : rows) {
            dtos.add(mapToDTO(row));
        }
        return dtos;
    }

    public List<TableauDTO> getHistoriqueAllUsers() {
        List<Object[]> results = walletRepository.findHistoriqueAllUser();
        return this.mapToDTOList(results);
    }

}
