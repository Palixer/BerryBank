package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;

    @RequestMapping("/cards")
    public List<CardDTO> findAll() {
        return cardService.findAll();
    }

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping(path = "/clients/current/cards")
    public ResponseEntity<Object> postAccountDTO(Authentication authentication,
    @RequestParam CardType cardType, @RequestParam CardColor cardColor) {
        Card newCard = new Card(CardUtils.getCardNumber(1000,9999),CardUtils.getCVVNumber(100,999),cardType,cardColor); //NUEVO++***
        return cardService.createCard(authentication, newCard, cardType);

    }



}
