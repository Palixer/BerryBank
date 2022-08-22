package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @RequestMapping("/cards")
    public List<CardDTO> findAll() {
        return cardRepository.findAll().stream().map(card -> new CardDTO(card)).collect(Collectors.toList());
    }
    @Autowired
    private ClientRepository clientRepository;

    @PostMapping(path = "/clients/current/cards")
    public ResponseEntity<Object> postAccountDTO(Authentication authentication,
    @RequestParam CardType cardType, @RequestParam CardColor cardColor) {
        Client newClient= this.clientRepository.findByEmail(authentication.getName());

        if (cardRepository.findByTypeAndClient(cardType, newClient).size()>2){
            return new ResponseEntity<>("3 cards max", HttpStatus.FORBIDDEN);
        }

        /*
        if (cardRepository.findByColor(cardColor, newClient).size()>2){
            return new ResponseEntity<>("Solo podés tener 3 tarjetas del mismo color", HttpStatus.FORBIDDEN);
        }

         */



        Card newCard = new Card(CardUtils.getCardNumber(1000,9999),CardUtils.getCVVNumber(100,999),cardType,cardColor,newClient); //NUEVO++***



        cardRepository.save(newCard);


        return new ResponseEntity<>("New card created", HttpStatus.CREATED);

    }



}
