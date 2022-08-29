package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.List;

public interface AccountService {
    List<AccountDTO> getAllAccounts();
    AccountDTO getAccount(Long id);

    List<AccountDTO> getAccounts(Authentication authentication);
    ResponseEntity<Object> createAccount(Authentication authentication, Account newAccount);

    Account findByNumber(String number);

}
