package itu.crypto.service.transaction.fund;

import com.google.firebase.messaging.FirebaseMessagingException;
import itu.crypto.entity.fund.PendingMvFund;
import itu.crypto.firebase.firestore.generalisation.BaseService;
import itu.crypto.repository.transaction.fund.PendingMvFundRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PendingMvFundService implements BaseService<PendingMvFund> {

    private final PendingMvFundRepository pendingMvFundRepository;

    public List<PendingMvFund> findAll() {
        return this.pendingMvFundRepository.findAll();
    }

    @Transactional
    public PendingMvFund save(PendingMvFund pendingMvFund) throws FirebaseMessagingException {
        return pendingMvFundRepository.save(pendingMvFund);
    }
}
