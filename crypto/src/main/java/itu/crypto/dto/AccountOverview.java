package itu.crypto.dto;

import itu.crypto.entity.Account;

public interface AccountOverview {
    double getSumSales();
    double getSumPurchases();
    Account getAccount();
}
