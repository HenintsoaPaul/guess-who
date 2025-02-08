package itu.crypto.service.transaction.fund;

import itu.crypto.entity.account.Account;
import itu.crypto.entity.fund.PendingMvFund;
import itu.crypto.entity.fund.PendingMvFundException;
import itu.crypto.entity.fund.PendingState;
import itu.crypto.entity.fund.TypeMvFund;
import itu.crypto.firebase.firestore.generalisation.BaseService;
import itu.crypto.repository.transaction.fund.PendingMvFundRepository;
import itu.crypto.repository.transaction.fund.PendingStateRepository;
import itu.crypto.repository.transaction.fund.TypeMvFundRepository;
import itu.crypto.service.EmailService;
import itu.crypto.service.account.AccountService;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PendingMvFundService implements BaseService<PendingMvFund> {

    private final EmailService emailService;

    private final MvFundService mvFundService;
    private final PendingMvFundRepository pendingMvFundRepository;

    // for test purposes
    private final AccountService accountService;
    private final PendingStateRepository pendingStateRepository;
    private final TypeMvFundRepository typeMvFundRepository;

    public PendingState getEtatAttente() {
        return pendingStateRepository.findById(1).orElseThrow();
    }

    public List<PendingMvFund> findAll() {
        return this.pendingMvFundRepository.findAll();
    }

    public List<Account> findAllAccounts() {
        return this.accountService.findAll();
    }

    public List<TypeMvFund> findAllTypeMvFundsDepotRetrait() {
        return this.typeMvFundRepository.findAll().stream()
                .filter(tmf -> (tmf.getId() == 1 || tmf.getId() == 2))
                .collect(Collectors.toList());
    }

    public Optional<PendingMvFund> findById(int id) {
        return this.pendingMvFundRepository.findById(id);
    }

    public List<PendingMvFund> findAllAttente() {
        return pendingMvFundRepository.findAllAttente();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateOrCreate(PendingMvFund pendingMvFund) {
        pendingMvFundRepository.save(pendingMvFund);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(int id) {
        pendingMvFundRepository.deleteById(id);
    }

    private void verificationValidable(PendingMvFund pmf) throws PendingMvFundException {
        if (pmf.getPendingState().getId() == 2) {
            throw new PendingMvFundException("Demande deja validee!");
        } else if (pmf.getPendingState().getId() == 3) {
            throw new PendingMvFundException("Demande deja refusee!");
        }
    }

    /**
     * Validation d'un depot/retrait
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public PendingMvFund validate(int id) throws PendingMvFundException {
        PendingMvFund pmf = pendingMvFundRepository.findById(id).orElseThrow();
        verificationValidable(pmf);

        // controle
        double solde = 0;
        if (pmf.getTypeMvFund().deTypeRetrait()) {
            solde = pmf.controlAmountRetrait();
        } else if (pmf.getTypeMvFund().deTypeDepot()) {
            solde = pmf.getAmount() + pmf.getAccount().getFund();
        }

        // mampihena
        Account a = pmf.getAccount();
        a.setFund(solde);
        accountService.save(a);

        // validation
        pmf.setDateValidation(LocalDateTime.now());
        pmf.setPendingState(pendingStateRepository.findById(2).orElseThrow());

        return this.save(pmf);
    }

    /**
     * Refus d'un depot/retrait
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public PendingMvFund refus(int id) throws PendingMvFundException {
        PendingMvFund pmf = pendingMvFundRepository.findById(id).orElseThrow();
        verificationValidable(pmf);

        // refus
        pmf.setDateValidation(LocalDateTime.now());
        pmf.setPendingState(pendingStateRepository.findById(3).orElseThrow());

        return this.save(pmf);
    }

    /**
     * Insert ou update d'une demande.
     * Si l'etat devient validee, nous creons la mv_fund pour la demande.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public PendingMvFund save(PendingMvFund pmf) {
        pendingMvFundRepository.save(pmf);

        PendingState etat = pmf.getPendingState();
        if (pmf.getDateValidation() != null && etat.getId() == 2) {
            mvFundService.addFromPending(pmf);
        }

        return pmf;
    }

    public String sendEmail(PendingMvFund pmf) {
        PendingState etat = pmf.getPendingState();
        if (pmf.getDateValidation() == null && etat.getId() == 1) {
            return emailService.writeEmailAttente(pmf);
        } else {
            return emailService.writeEmailReponse(pmf);
        }
    }

    public PendingMvFund cobaieAttente() {
        PendingState pendingState = pendingStateRepository.findAll().get(0);
        TypeMvFund tmf = typeMvFundRepository.findAll().get(1); // retrait
        Account account = accountService.findAll().get(0);

        return new PendingMvFund(null,
                LocalDateTime.now(),
                null,
                1230000000000.00,
                pendingState,
                account,
                tmf);
    }

    public PendingMvFund cobaieValide() {
        PendingState pendingState = pendingStateRepository.findAll().get(0);
        TypeMvFund tmf = typeMvFundRepository.findAll().get(1); // retrait
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
