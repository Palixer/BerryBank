package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface LoanService {
    List<LoanDTO> getLoans();
    Loan findById(Long id);

    ResponseEntity<Object> createClientLoan(Account accountFind, ClientLoan newClientLoan, LoanApplicationDTO loanApplicationDTO);

}
