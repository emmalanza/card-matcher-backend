package com.cardmatcher.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardmatcher.backend.models.User;
import com.cardmatcher.backend.repositories.UserRepository;
import com.cardmatcher.backend.services.CardListService;

@RestController
@RequestMapping("/api/cardlists")
public class CardListController {

    @Autowired
    private CardListService cardListService;

    @Autowired
    private UserRepository userRepository;

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

    @GetMapping("/user")
    public  ResponseEntity<?> getUserCardLists(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }

        String username = userDetails.getUsername();
        System.out.println("Usuario encontrado: " + username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ResponseEntity.ok(cardListService.getCardListsByUser(user));
    }

}