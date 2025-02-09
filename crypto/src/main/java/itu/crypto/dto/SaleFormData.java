package itu.crypto.dto;

import itu.crypto.entity.account.Account;
import itu.crypto.entity.sale.Sale;
import itu.crypto.entity.sale.SaleDetail;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SaleFormData {
    private Sale sale;
    private List<SaleDetail> saleDetails = new ArrayList<>();

    public SaleFormData(Account account) {
	sale = new Sale();
	sale.setAccount(account);
	saleDetails.add( new SaleDetail() );
    }
}
