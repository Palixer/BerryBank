package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.*;

import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

import java.util.stream.Collectors;



@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @Autowired
    private LoanService loanService;



    @GetMapping("/loans")
    public List<LoanDTO> getLoans(){
        return loanService.getLoans();
    }


    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createClientLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){

        Loan loanFind = this.loanService.findById(loanApplicationDTO.getId());//guardo la info del prestamo en una variable
        Account accountFind = this.accountService.findByNumber(loanApplicationDTO.getToAccountNumber());
        Client clientFind = this.clientService.findByEmail(authentication.getName());

        if(loanApplicationDTO.getId()==0){
            return new ResponseEntity<>("Not an available ID", HttpStatus.FORBIDDEN);
        }

        if(loanApplicationDTO.getAmount()==0 || loanApplicationDTO.getAmount()>loanFind.getMaxAmount()){
            return new ResponseEntity<>("Exceeds the maximum available", HttpStatus.FORBIDDEN);
        }

        if(!loanFind.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("Not an available amount of payments", HttpStatus.FORBIDDEN);
        }

        if(accountFind.getNumber().isEmpty()){
            return new ResponseEntity<>("In Account", HttpStatus.FORBIDDEN);
        }

        ClientLoan newClientLoan= new ClientLoan(loanApplicationDTO.getAmount()+(loanApplicationDTO.getAmount()*20/100),loanApplicationDTO.getPayments(),clientFind,loanFind);



        return new ResponseEntity<>("Transaccion realizada con exito", HttpStatus.CREATED);

}


}


