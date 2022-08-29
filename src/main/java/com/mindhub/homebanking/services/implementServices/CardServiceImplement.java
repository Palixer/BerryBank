package com.mindhub.homebanking.services.implementServices;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImplement implements CardService {

    @Autowired
    CardRepository cardRepository;
    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<CardDTO> findAll() {
        return cardRepository.findAll().stream().map(card -> new CardDTO(card)).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Object> createCard(Authentication authentication, Card newCard, CardType cardType) {
        Client newClient= this.clientRepository.findByEmail(authentication.getName());

        if (cardRepository.findByTypeAndClient(cardType, newClient).size()>2){
            return new ResponseEntity<>("3 cards max", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("New card created", HttpStatus.CREATED);
    }

}
