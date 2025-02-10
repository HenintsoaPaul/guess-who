package itu.crypto.service.account;

import itu.crypto.entity.account.Account;
import itu.crypto.entity.account.Admin;
import itu.crypto.repository.account.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public List<Admin> findByAccount(Account account) {
        return adminRepository.findByAccount(account);
    }
}
