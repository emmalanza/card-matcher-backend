package com.cardmatcher.backend.services;

import com.cardmatcher.backend.models.Card;
import com.cardmatcher.backend.models.Set;
import com.cardmatcher.backend.models.dtos.cards.CardDTO;
import com.cardmatcher.backend.models.dtos.cards.CardResponseDTO;
import com.cardmatcher.backend.repositories.CardRepository;
import com.cardmatcher.backend.repositories.SetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final SetRepository setRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String BASE_URL = "https://api.tcgdex.net/v2/en/cards/";

    public CardService(CardRepository cardRepository, SetRepository setRepository) {
        this.cardRepository = cardRepository;
        this.setRepository = setRepository;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public void loadCardsFromAPI() {
        List<Set> sets = setRepository.findAll();

        for (Set set : sets) {
            String setId = set.getId();
            int totalCards = set.getNumOfTotalCards();

            for (int i = 1; i <= totalCards; i++) {
                String cardNumber = String.format("%03d", i);
                String url = BASE_URL + setId + "-" + cardNumber;
                ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
                String rawJsonResponse = responseEntity.getBody();

                try {
                    CardDTO cardResponse = objectMapper.readValue(rawJsonResponse, CardDTO.class);

                    if (cardResponse != null) {
                        Optional<Card> existingCard = cardRepository.findById(cardResponse.getId());
                        if (existingCard.isEmpty()) {
                            Card card = new Card();
                            card.setId(cardResponse.getId());
                            card.setName(cardResponse.getName());
                            card.setCategory(cardResponse.getCategory());
                            card.setImgUrl(cardResponse.getImgUrl());
                            card.setRarity(Card.Rarity.valueOf(cardResponse.getRarity()));
                            card.setSet(set);
                            card.setIsInterchangeable();

                            cardRepository.save(card);
                        } else {
                            System.out.println("Card " + cardResponse.getId() + " already exists.");
                        }
                    } else {
                        System.out.println("Error loading card with ID: " + setId + "-" + cardNumber);
                    }

                } catch (Exception e) {
                    System.err.println("Error getting card with URL: " + url);
                    e.printStackTrace();
                }
            }
        }
    }

    public List<CardResponseDTO> getCardsBySetId(String setId) {
        List<Card> cards = cardRepository.findBySetId(setId);
        List<CardResponseDTO> cardResponseDTOs = new ArrayList<>();

        for (Card card : cards) {
            CardResponseDTO dto = new CardResponseDTO();
            dto.setId(card.getId());
            dto.setName(card.getName());
            dto.setImgUrl(card.getImgUrl());
            dto.setCategory(card.getCategory());
            dto.setRarity(card.getRarity().name());
            dto.setInterchangeable(card.getIsInterchangeable());
            dto.setSetName(card.getSet().getName());

            cardResponseDTOs.add(dto);
        }

        return cardResponseDTOs;
    }

    public List<CardResponseDTO> getTradableCards() {
        
        List<Card> cards = cardRepository.findByIsInterchangeableTrue();
        List<CardResponseDTO> cardResponseDTOs = new ArrayList<>();

        for (Card card : cards) {
            CardResponseDTO dto = new CardResponseDTO();
            dto.setId(card.getId());
            dto.setName(card.getName());
            dto.setImgUrl(card.getImgUrl());
            dto.setCategory(card.getCategory());
            dto.setRarity(card.getRarity().name());
            dto.setInterchangeable(card.getIsInterchangeable());
            dto.setSetName(card.getSet().getName());

            cardResponseDTOs.add(dto);
        }

        return cardResponseDTOs;
    }
    
}
