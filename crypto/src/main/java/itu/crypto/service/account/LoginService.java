package itu.crypto.service.account;

import itu.crypto.api.ApiResponse;
import itu.crypto.dto.login.LoginRequest;
import itu.crypto.entity.account.Account;
import itu.crypto.api.FetchService;
import itu.crypto.entity.account.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final FetchService fetchService;
    private final AccountService accountService;
    private final AdminService adminService;

    public ApiResponse sendLoginDto(LoginRequest loginRequest) {
        return fetchService.fetchUrl("/api/login", loginRequest, true);
    }

    public ApiResponse sendPin(LoginRequest loginRequest) {
        return fetchService.fetchUrl("/api/login/validate", loginRequest, true);
    }

    public Account getAccount(LoginRequest loginRequest) {
        return this.accountService.findByEmail(loginRequest.getEmail());
    }

    public Admin getAdminStatus(Account account) {
        List<Admin> adminList = adminService.findByAccount(account);
        return adminList.isEmpty() ? null : adminList.get(0);
    }
}
