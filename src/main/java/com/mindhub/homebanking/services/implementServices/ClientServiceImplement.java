package com.mindhub.homebanking.services.implementServices;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.AccountUtils.generateAccountNumber;

@Service
public class ClientServiceImplement implements ClientService {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<ClientDTO> findAll() {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClientById(Long id) {
       return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }

    @Override
    public ClientDTO getClient(Authentication authentication) {
        Client client = this.clientRepository.findByEmail(authentication.getName());
        return new ClientDTO(client);
    }

    @Override

    public ResponseEntity<Object> createNewClient(Client newClient, Account newAccount) {
        clientRepository.save(newClient);
        accountRepository.save(newAccount);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }


}
