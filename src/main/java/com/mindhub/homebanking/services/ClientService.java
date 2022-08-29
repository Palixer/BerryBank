package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ClientService {

    List<ClientDTO> findAll();
    ClientDTO getClientById(Long id);
    ClientDTO getClient(Authentication authentication);

    ResponseEntity<Object> createNewClient(Client newClient, Account newAccount);

    Client findByEmail(String email);
}
