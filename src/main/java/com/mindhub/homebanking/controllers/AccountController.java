package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.mindhub.homebanking.repositories.ClientRepository;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.AccountUtils.generateAccountNumber;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

  @Autowired
  private AccountService accountService;

    @RequestMapping("/accounts")
    public List<AccountDTO> findAll() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccountById (@PathVariable Long id){
        return accountService.getAccount(id);
    }


    @Autowired
    private ClientRepository clientRepository;


    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication){
        return accountService.getAccounts(authentication);
    };

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> postAccountDTO(Authentication authentication) throws IOException {
        Account newAccount = new Account(generateAccountNumber(),0.00);
       return accountService.createAccount(authentication, newAccount);
    }



}
