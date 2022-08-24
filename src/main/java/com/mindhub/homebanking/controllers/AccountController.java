package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.mindhub.homebanking.repositories.ClientRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> findAll() {
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(toList());
    }
    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccountById (@PathVariable Long id, Authentication authentication){

        Client client = this.clientRepository.findByEmail(authentication.getName());
        Set<Account> cuentas = client.getAccounts();

        Iterator iter = cuentas.iterator();
        while (iter.hasNext()){
            Account account = (Account) iter.next();
            if(account.getId() == id){
                return this.accountRepository.findById(id).map(AccountDTO::new).orElse(null);
            }
        }

        //return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
        return null;
    }



    @Autowired
    private ClientRepository clientRepository;

   /* @GetMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication){
        Client client = this.clientRepository.findByEmail(authentication.getName());
        return new ClientDTO(client);
    }*/

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication){
        Client client = this.clientRepository.findByEmail((authentication.getName()));
        return client.getAccounts().stream().map(AccountDTO::new).collect(toList());
    };

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> postAccountDTO(Authentication authentication){
        Client newClient= this.clientRepository.findByEmail(authentication.getName());

        Account newAccount = new Account("VIN-"+getRandomNumber(1000000,10000000),0.00,newClient);

        accountRepository.save(newAccount);

        if(newClient.getAccounts().size()>2){
            return new ResponseEntity<>("Limit of accounts reached", HttpStatus.FORBIDDEN);
        } else{
           return new ResponseEntity<>("New account created", HttpStatus.CREATED);
        }

    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
