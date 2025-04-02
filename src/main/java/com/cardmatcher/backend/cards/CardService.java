package com.cardmatcher.backend.cards;

import com.cardmatcher.backend.sets.Set;
import com.cardmatcher.backend.sets.SetRepository;
import com.cardmatcher.backend.utils.RarityMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
                System.out.println("Raw response from API: " + rawJsonResponse);

                try {

                    CardDTO cardResponse = objectMapper.readValue(rawJsonResponse, CardDTO.class);

                    if (cardResponse != null) {
                        Optional<Card> existingCard = cardRepository.findById(cardResponse.getId());
                        if (existingCard.isEmpty()) {
                            Card card = new Card();
                            card.setName(cardResponse.getName());
                            card.setId(cardResponse.getId());
                            card.setImgUrl(cardResponse.getImgUrl());
                            card.setCategory(cardResponse.getCategory());
                            card.setRarity(RarityMapper.mapRarity(cardResponse.getRarity()));
                            card.setIsInterchangeable();
                            card.setSet(set);

                            cardRepository.save(card);
                            System.out.println("Card loaded: " + cardResponse.getName());
                        } else {
                            System.out.println("Card " + cardResponse.getId() + " already exists.");
                        }
                    } else {
                        System.out.println("Err loading card with ID: " + setId + "-" + cardNumber);
                    }

                } catch (Exception e) {
                    System.err.println("Err getting card with URL: " + url);
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Card> getCardsBySetId(String setId) {
        return cardRepository.findBySetId(setId);
    }
}
