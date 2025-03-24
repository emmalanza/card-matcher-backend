package com.cardmatcher.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardmatcher.backend.services.CardService;

@RestController
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/load-cards")
    public String loadCards() {
        cardService.loadCardsFromAPI();
        return "Cards loaded!";
    }

}
