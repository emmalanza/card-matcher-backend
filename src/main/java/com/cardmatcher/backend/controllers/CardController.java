package com.cardmatcher.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cardmatcher.backend.models.Card;
import com.cardmatcher.backend.services.CardService;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping("/load-cards")
    public String loadCards() {
        cardService.loadCardsFromAPI();
        return "Cards loaded!";
    }

    @GetMapping("/set")
    public List<Card> getCardsBySetId(@RequestParam String setId) {
        return cardService.getCardsBySetId(setId);
    }

    // Endpoint para obtener cartas intercambiables
    @GetMapping("/tradable")
    public ResponseEntity<List<Card>> getTradableCards() {
        List<Card> tradableCards = cardService.getTradableCards();
        return ResponseEntity.ok(tradableCards);
    }

}
