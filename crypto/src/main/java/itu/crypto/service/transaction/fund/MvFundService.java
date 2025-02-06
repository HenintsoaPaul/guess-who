package itu.crypto.service.transaction.fund;

import com.google.firebase.messaging.FirebaseMessagingException;

import itu.crypto.entity.fund.MvFund;
import itu.crypto.entity.fund.PendingMvFund;
import itu.crypto.firebase.firestore.generalisation.BaseService;
import itu.crypto.repository.transaction.fund.MvFundRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MvFundService implements BaseService<MvFund> {

    private final MvFundRepository mvFundRepository;

    public List<MvFund> findAll() {
        return this.mvFundRepository.findAll();
    }

    @Transactional
    public MvFund save(MvFund mvFund) {
        return mvFundRepository.save(mvFund);
    }

    public Optional<MvFund> findByPendingMvFund(PendingMvFund pendingMvFund) {
        return mvFundRepository.findByIdPendingMvFund(pendingMvFund.getId());
    }

    /**
     * Verifie si un {@code mv_fund} est deja lie au pending_mv_fund. Si aucun mv_fund n'est
     * trouve, nous ajoutons un nouveau mv_fund pour cette pending_mv_fund. Sinon, nous ne
     * faisons rien et nous retournons null.
     */
    @Transactional
    public MvFund addFromPending(PendingMvFund pmf) {
        MvFund fille = findByPendingMvFund(pmf).orElse(null);
        if (fille == null) {
            fille = new MvFund(pmf);
            return this.save(fille);
        }
        return fille;
    }
}
