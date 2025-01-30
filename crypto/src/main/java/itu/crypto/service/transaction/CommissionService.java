package itu.crypto.service.transaction;

import itu.crypto.entity.commission.Commission;
import itu.crypto.entity.commission.CommissionType;
import itu.crypto.repository.transaction.CommissionRepository;
import itu.crypto.repository.transaction.CommissionTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommissionService {

    private final CommissionRepository commissionRepository;
    private final CommissionTypeRepository commissionTypeRepository;

    public List<Commission> findAll() {
        return commissionRepository.findAll();
    }

    public List<CommissionType> findAllTypes() {
        return commissionTypeRepository.findAll();
    }

    @Transactional
    public void save(Commission commission) throws Exception {
        throw new Exception("Not implemented");
    }
}
