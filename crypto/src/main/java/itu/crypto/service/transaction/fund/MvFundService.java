package itu.crypto.service.transaction.fund;

import itu.crypto.entity.fund.MvFund;
import itu.crypto.entity.fund.PendingMvFund;
import itu.crypto.firebase.firestore.generalisation.BaseService;
import itu.crypto.repository.transaction.fund.MvFundRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MvFundService implements BaseService<MvFund> {

    private final MvFundRepository mvFundRepository;

    public List<MvFund> findAll() {
        return this.mvFundRepository.findAll();
    }

    public Optional<MvFund> findByPendingMvFund(PendingMvFund pendingMvFund) {
        return mvFundRepository.findByIdPendingMvFund(pendingMvFund.getId());
    }

    @Override
    public Optional<MvFund> findById(int id) {
        return mvFundRepository.findById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateOrCreate(MvFund mvFund) {
        if (mvFund.getIdSource() == null) mvFund.setIdSource(0);

        int retries = 3;
        while (retries > 0) {
            try {
                mvFundRepository.save(mvFund);
                return;
            } catch (ObjectOptimisticLockingFailureException e) {
                log.warn("Conflit de mise à jour sur MvFund ID {}. Tentative restante: {}", mvFund.getId(), retries - 1);
                retries--;
            }
        }
        throw new RuntimeException("Maj MvFund après plusieurs tentatives (mety fa misy exception)");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(int id) {
        mvFundRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public MvFund save(MvFund mvFund) {
        return mvFundRepository.save(mvFund);
    }

    /**
     * Verifie si un {@code mv_fund} est deja lie au pending_mv_fund. Si aucun mv_fund n'est
     * trouve, nous ajoutons un nouveau mv_fund pour cette pending_mv_fund. Sinon, nous ne
     * faisons rien et nous retournons null.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addFromPending(PendingMvFund pmf) {
        MvFund fille = findByPendingMvFund(pmf).orElse(null);
        if (fille == null) {
            fille = new MvFund(pmf);
            this.save(fille);
        }
    }
}
