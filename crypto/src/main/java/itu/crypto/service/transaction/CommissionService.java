package itu.crypto.service.transaction;

import itu.crypto.entity.commission.Commission;
import itu.crypto.entity.commission.CommissionType;
import itu.crypto.repository.transaction.CommissionRepository;
import itu.crypto.repository.transaction.CommissionTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Commission> findCurrentCommissions() {
        return commissionTypeRepository.findAll().stream()
                .map(ct -> commissionRepository.findLatestByType(ct.getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(Commission commission) throws Exception {
        commissionRepository.save(commission);
    }
}
