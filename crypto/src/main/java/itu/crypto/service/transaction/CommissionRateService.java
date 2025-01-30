package itu.crypto.service.transaction;

import itu.crypto.entity.commission.CommissionRate;
import itu.crypto.entity.commission.CommissionType;
import itu.crypto.repository.transaction.CommissionRateRepository;
import itu.crypto.repository.transaction.CommissionTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommissionRateService {

    private final CommissionRateRepository commissionRateRepository;
    private final CommissionTypeRepository commissionTypeRepository;

    public List<CommissionRate> findAll() {
        return commissionRateRepository.findAll();
    }

    public List<CommissionType> findAllTypes() {
        return commissionTypeRepository.findAll();
    }

    public List<CommissionRate> findCurrentCommissions() {
        return commissionTypeRepository.findAll().stream()
                .map(ct -> commissionRateRepository.findLatestByType(ct.getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(CommissionRate commissionRate) throws Exception {
        commissionRateRepository.save(commissionRate);
    }
}
