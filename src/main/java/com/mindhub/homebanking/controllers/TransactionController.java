package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/transactions")
    public ResponseEntity<Object> postAccountDTO(
            Authentication authentication,
            @RequestParam Double amount, @RequestParam String description,
            @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber){


        if(amount == 0 || description.isEmpty() || toAccountNumber.isEmpty() || fromAccountNumber.isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        Account cuentaOrigen= this.accountRepository.findByNumber(fromAccountNumber);
        if(cuentaOrigen==null){
            return new ResponseEntity<>("Nonexistent Account", HttpStatus.FORBIDDEN);
        }

        Client autClient = this.clientRepository.findByEmail(authentication.getName());
        if(!autClient.getAccounts().contains(cuentaOrigen)){
            return new ResponseEntity<>("This is not your Account", HttpStatus.FORBIDDEN);
        }

        Account cuentaDestino= this.accountRepository.findByNumber(toAccountNumber);
        if(cuentaDestino==null){
            return new ResponseEntity<>("Nonexistent destination Account ", HttpStatus.FORBIDDEN);
        }

        if(cuentaOrigen.equals(cuentaDestino)){
            return new ResponseEntity<>("Same account", HttpStatus.FORBIDDEN);
        }

        if(cuentaOrigen.getBalance()<amount){
            return new ResponseEntity<>("Insuficient money", HttpStatus.FORBIDDEN);
        }


        Transaction newTransaction1 = new Transaction(TransactionType.DEBIT, -amount,description,fromAccountNumber,toAccountNumber);
        Transaction newTransaction2 = new Transaction(TransactionType.CREDIT, amount,description,fromAccountNumber,toAccountNumber);

        transactionRepository.save(newTransaction1);
        transactionRepository.save(newTransaction2);

        cuentaOrigen.setBalance(cuentaOrigen.getBalance()-amount);
        cuentaDestino.setBalance((cuentaDestino.getBalance()+amount));

        accountRepository.save(cuentaOrigen);
        accountRepository.save(cuentaDestino);

        return new ResponseEntity<>("Succesful transaction", HttpStatus.CREATED);


    }
}
