package com.cardmatcher.backend.controllers;

import com.cardmatcher.backend.models.dtos.ApiResponseDTO;
import com.cardmatcher.backend.models.dtos.cards.CardResponseDTO;
import com.cardmatcher.backend.services.CardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/load-cards")
    public ResponseEntity<ApiResponseDTO<String>> loadCards() {
        try {
            cardService.loadCardsFromAPI();
            ApiResponseDTO<String> response = new ApiResponseDTO<>("Cartas cargadas exitosamente.", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponseDTO<String> response = new ApiResponseDTO<>("Error al cargar las cartas: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/set")
    public ResponseEntity<ApiResponseDTO<List<CardResponseDTO>>> getCardsBySetId(@RequestParam String setId) {
        try {
            List<CardResponseDTO> cards = cardService.getCardsBySetId(setId);
            ApiResponseDTO<List<CardResponseDTO>> response = new ApiResponseDTO<>(cards, "Cartas obtenidas correctamente.", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponseDTO<List<CardResponseDTO>> response = new ApiResponseDTO<>(null, "No se encontraron cartas para el set especificado.", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/tradable")
    public ResponseEntity<ApiResponseDTO<List<CardResponseDTO>>> getTradableCards() {
        try {
            List<CardResponseDTO> tradableCards = cardService.getTradableCards();
            ApiResponseDTO<List<CardResponseDTO>> response = new ApiResponseDTO<>(tradableCards, "Cartas intercambiables obtenidas correctamente.", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponseDTO<List<CardResponseDTO>> response = new ApiResponseDTO<>(null, "Error al obtener las cartas intercambiables: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
