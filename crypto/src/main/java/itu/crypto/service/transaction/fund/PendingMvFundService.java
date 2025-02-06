package itu.crypto.service.transaction.fund;

import com.google.firebase.messaging.FirebaseMessagingException;
import itu.crypto.entity.account.Account;
import itu.crypto.entity.fund.PendingMvFund;
import itu.crypto.entity.fund.PendingState;
import itu.crypto.entity.fund.TypeMvFund;
import itu.crypto.firebase.firestore.generalisation.BaseService;
import itu.crypto.repository.transaction.fund.PendingMvFundRepository;
import itu.crypto.repository.transaction.fund.PendingStateRepository;
import itu.crypto.repository.transaction.fund.TypeMvFundRepository;
import itu.crypto.service.account.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PendingMvFundService implements BaseService<PendingMvFund> {

    private final MvFundService mvFundService;
    private final PendingMvFundRepository pendingMvFundRepository;

    // for test purposes
    private final AccountService accountService;
    private final PendingStateRepository pendingStateRepository;
    private final TypeMvFundRepository typeMvFundRepository;

    public List<PendingMvFund> findAll() {
        return this.pendingMvFundRepository.findAll();
    }

    @Transactional
    public PendingMvFund save(PendingMvFund pmf) throws FirebaseMessagingException {
        pendingMvFundRepository.save(pmf);

        if (pmf.isValidated()) {
            mvFundService.addFromPending(pmf);
        }

        return null;
    }

    public PendingMvFund cobaieAttente() {
        PendingState pendingState = pendingStateRepository.findAll().get(0);
        TypeMvFund tmf = typeMvFundRepository.findAll().get(0);
        Account account = accountService.findAll().get(0);

        return new PendingMvFund(null,
                LocalDateTime.now(),
                null,
                123.00,
                pendingState,
                account,
                tmf);
    }

    public PendingMvFund cobaieValide() {
        PendingState pendingState = pendingStateRepository.findAll().get(0);
        TypeMvFund tmf = typeMvFundRepository.findAll().get(0);
        Account account = accountService.findAll().get(0);

        return new PendingMvFund(null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                123.00,
                pendingState,
                account,
                tmf);
    }
}
