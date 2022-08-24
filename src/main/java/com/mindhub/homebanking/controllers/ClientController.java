package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.mindhub.homebanking.repositories.AccountRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/clients")
    public List<ClientDTO> findAll() {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClientById (@PathVariable Long id){

        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }
    //metodo para abrir el cliente que esta la sesion iniciada
    //Authentication
    @GetMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication){
        Client client = this.clientRepository.findByEmail(authentication.getName());

        return new ClientDTO(client);
    }
    @Autowired

    private PasswordEncoder passwordEncoder;
    @PostMapping(path = "/clients")

    public ResponseEntity<Object> createClient(

            @RequestParam String firstName, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {



        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }



        if (clientRepository.findByEmail(email) !=  null) {

            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);

        }

        Client newClient=new Client(firstName, lastName, email, passwordEncoder.encode(password));

        clientRepository.save(newClient);

        Account newAccount= new Account("VIN-"+getRandomNumber(1000000,10000000),0.00,newClient);
        accountRepository.save(newAccount);


        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


}


