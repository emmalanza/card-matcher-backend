package com.cardmatcher.backend.controllers;

import com.cardmatcher.backend.models.dtos.ApiResponseDTO;
import com.cardmatcher.backend.models.dtos.cards.CardDTO;
import com.cardmatcher.backend.services.CardListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cardlists")
public class CardListController {

    private final CardListService cardListService;

  
    @Autowired
    public CardListController(CardListService cardListService) {
        this.cardListService = cardListService;
    }

    @PostMapping("/{cardListId}/cards/{cardId}")
    public ResponseEntity<ApiResponseDTO<CardDTO>> addCardToCardList(
            @PathVariable Long cardListId,
            @PathVariable String cardId) {
        try {

            CardDTO cardDTO = cardListService.addCardToCardList(cardListId, cardId);
            return ResponseEntity.ok(new ApiResponseDTO<>(cardDTO, "Carta añadida correctamente", HttpStatus.OK.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDTO<>(null, e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/{cardListId}/cards/{cardId}")
    public ResponseEntity<ApiResponseDTO<CardDTO>> removeCardFromCardList(
            @PathVariable Long cardListId,
            @PathVariable String cardId) {
        try {

            CardDTO cardDTO = cardListService.removeCardFromCardList(cardListId, cardId);
            return ResponseEntity.ok(new ApiResponseDTO<>(cardDTO, "Carta eliminada correctamente", HttpStatus.OK.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDTO<>(null, e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

}
