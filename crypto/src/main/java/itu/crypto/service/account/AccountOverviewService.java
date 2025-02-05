package itu.crypto.service.account;

import itu.crypto.dto.AccountOverview;
import itu.crypto.entity.account.Account;
import itu.crypto.entity.Purchase;
import itu.crypto.repository.account.AccountRepository;
import itu.crypto.service.transaction.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountOverviewService {
    private final AccountRepository accountRepository;
    private final PurchaseService purchaseService;

    AccountOverview findAccountOverview(Account account, List<Purchase> purchases) {
        Double sumSales = purchases.stream()
                .filter(p -> p.getAccountSeller().equals(account))
                .mapToDouble(Purchase::getTotalPrice)
                .sum();

        Double sumPurchase = purchases.stream()
                .filter(p -> p.getAccountPurchaser().equals(account))
                .mapToDouble(Purchase::getTotalPrice)
                .sum();

        return new AccountOverview() {
            @Override
            public double getSumSales() {
                return sumSales;
            }

            @Override
            public double getSumPurchases() {
                return sumPurchase;
            }

            @Override
            public Account getAccount() {
                return account;
            }
        };
    }

    public List<AccountOverview> findAllAccountOverview(LocalDateTime from, LocalDateTime to) {
        List<Purchase> purchases = (from == null && to == null) ?
                purchaseService.findAll() : purchaseService.findAllByDatePurchaseInRange(from, to);

        return accountRepository.findAll().stream()
                .map(a -> findAccountOverview(a, purchases))
                .collect(Collectors.toList());
    }
}
