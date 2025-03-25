package com.cardmatcher.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cardmatcher.backend.models.Card;
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

     @GetMapping("/api/cards")
    public List<Card> getCardsBySetId(@RequestParam String setId) {
        return cardService.getCardsBySetId(setId);
    }

}
