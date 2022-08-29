package com.mindhub.homebanking.services.implementServices;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImplement implements AccountService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ClientRepository clientRepository;
    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(toList());
    }

    @Override
    public AccountDTO getAccount(Long id) {
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }

    @Override
    public List<AccountDTO> getAccounts(Authentication authentication) {
        Client client = this.clientRepository.findByEmail((authentication.getName()));
        return client.getAccounts().stream().map(AccountDTO::new).collect(toList());
    }

    @Override
    public ResponseEntity<Object> createAccount(Authentication authentication, Account newAccount) {
        Client newClient= this.clientRepository.findByEmail(authentication.getName());
        newClient.addAccount(newAccount);

        accountRepository.save(newAccount);

        if(newClient.getAccounts().size()>2){
            return new ResponseEntity<>("Limit of accounts reached", HttpStatus.FORBIDDEN);
        } else{
            return new ResponseEntity<>("New account created", HttpStatus.CREATED);
        }

    }

    @Override

    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }
}
