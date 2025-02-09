package itu.crypto.service.crypto;

import itu.crypto.entity.account.Account;
import itu.crypto.entity.crypto.Crypto;
import itu.crypto.entity.crypto.CryptoFav;
import itu.crypto.firebase.firestore.generalisation.BaseService;
import itu.crypto.repository.CryptoFavRepository;
import itu.crypto.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CryptoFavService implements BaseService<CryptoFav> {

    private final CryptoFavRepository cryptoFavRepository;
    private final CryptoService cryptoService;
    private final AccountService accountService;

    public List<CryptoFav> findAll() {
        return cryptoFavRepository.findAll();
    }

    public Optional<CryptoFav> findById(int id) {
        return cryptoFavRepository.findById(id);
    }

    public Crypto findCryptoById(Integer id) {
        return cryptoService.findById(id).orElseThrow();
    }

    public Account findAccountById(Integer id) {
        return accountService.findById(id).orElseThrow();
    }

    public Optional<CryptoFav> findByCryptoAndAccount(int idCrypto, int idAccount) {
        return cryptoFavRepository.findByCryptoAndAccount(idCrypto, idAccount);
    }

    /**
     * Lors du save, nous verifions qu'une ligne correspond deja a crypto-account,
     * si oui -> mettre a jour la ligne
     * si non -> nouvel insert
     * @param cryptoFav
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(CryptoFav cryptoFav) {
        CryptoFav efaAo = this.findByCryptoAndAccount(
                cryptoFav.getCrypto().getId(),
                cryptoFav.getAccount().getId()
        ).orElse(null);

        if (efaAo != null) {
            efaAo.setOnFav(!efaAo.isOnFav());
            efaAo.setDateCryptoFav(cryptoFav.getDateCryptoFav());
            cryptoFavRepository.save(efaAo);
        } else {
            cryptoFavRepository.save(cryptoFav);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateOrCreate(CryptoFav cryptoFav) {
        this.save(cryptoFav);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(int id) {
        cryptoFavRepository.deleteById(id);
    }
}
