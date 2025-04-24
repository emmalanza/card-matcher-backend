package com.cardmatcher.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardmatcher.backend.services.CardListService;

@RestController
@RequestMapping("/api/cardlists")
public class CardListController {

    @Autowired
    private CardListService cardListService;

    // Añadir una carta a una lista
    @PostMapping("/{cardListId}/cards/{cardId}")
    public ResponseEntity<Void> addCardToCardList(
            @PathVariable Long cardListId,
            @PathVariable String cardId) {
        cardListService.addCardToCardList(cardListId, cardId);
        return ResponseEntity.ok().build();
    }

    // Eliminar una carta de una lista
    @DeleteMapping("/{cardListId}/cards/{cardId}")
    public ResponseEntity<Void> removeCardFromCardList(
            @PathVariable Long cardListId,
            @PathVariable String cardId) {
        cardListService.removeCardFromCardList(cardListId, cardId);
        return ResponseEntity.noContent().build();
    }
}