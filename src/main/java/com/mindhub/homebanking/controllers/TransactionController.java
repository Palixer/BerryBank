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
    public ResponseEntity<Object> postTransactionDTO(Authentication authentication,
                                                     @RequestParam Double amount, @RequestParam String description,
                                                     @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber){



        if (amount ==0 || description.isEmpty() || toAccountNumber.isEmpty() || fromAccountNumber.isEmpty()) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }

        Account cuentaOrigen=this.accountRepository.findByNumber(fromAccountNumber);
        Account cuentaDestino;

        if (toAccountNumber.length()>10){
            cuentaDestino=this.accountRepository.findByCbu(toAccountNumber);
        } else{
            cuentaDestino=this.accountRepository.findByNumber(toAccountNumber);
        }


        if (cuentaOrigen==null){
            return new ResponseEntity<>("La cuenta de origen no existe", HttpStatus.FORBIDDEN);
        }

        Client autClient=this.clientRepository.findByEmail(authentication.getName());

        if (!autClient.getAccounts().contains(cuentaOrigen)){
            return new ResponseEntity<>("Esta cuenta no pertenece a este cliente", HttpStatus.FORBIDDEN);
        }

        if (cuentaDestino==null){
            return new ResponseEntity<>("La cuenta de destino no existe", HttpStatus.FORBIDDEN);
        }


        if (cuentaOrigen.equals(cuentaDestino)){
            return new ResponseEntity<>("No se puede realizar transacciones a la misma cuenta", HttpStatus.FORBIDDEN);
        }

        if (cuentaOrigen.getBalance()<amount){
            return new ResponseEntity<>("No se puede realizar la trasferencia por falta de fondos", HttpStatus.FORBIDDEN);
        }


        Transaction newTransaction1 = new Transaction(TransactionType.DEBIT,-amount,description,fromAccountNumber,toAccountNumber);
        Transaction newTransaction2=new Transaction(TransactionType.CREDIT,amount,description,fromAccountNumber,toAccountNumber);

        cuentaOrigen.addTransaction(newTransaction1);
        cuentaDestino.addTransaction(newTransaction2);

        transactionRepository.save(newTransaction1);
        transactionRepository.save(newTransaction2);

        cuentaOrigen.setBalance(cuentaOrigen.getBalance()-amount);
        cuentaDestino.setBalance(cuentaDestino.getBalance()+amount);

        accountRepository.save(cuentaDestino);
        accountRepository.save(cuentaOrigen);

        return new ResponseEntity<>("Transaccion realizada con exito", HttpStatus.CREATED);


    }
}
