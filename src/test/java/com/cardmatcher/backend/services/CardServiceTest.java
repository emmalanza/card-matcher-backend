package com.cardmatcher.backend.services;

import com.cardmatcher.backend.models.Card;
import com.cardmatcher.backend.models.Set;
import com.cardmatcher.backend.models.dtos.cards.CardResponseDTO;
import com.cardmatcher.backend.repositories.CardRepository;
import com.cardmatcher.backend.repositories.SetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private SetRepository setRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CardService cardService;

    private Set set;
    private Card card;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        set = new Set();
        set.setId("set1");
        set.setNumOfTotalCards(3);

        card = new Card();
        card.setId("card1");
        card.setName("Card 1");
        card.setRarity(Card.Rarity.DIAMOND_1);
        card.setCategory("Pokemon");
        card.setSet(set);
        card.setIsInterchangeable(true); 
    }

    @Test
    void testGetCardsBySetId() {
        when(cardRepository.findBySetId("set1")).thenReturn(List.of(card));

        List<CardResponseDTO> result = cardService.getCardsBySetId("set1");

        assertNotNull(result);
        assertEquals(1, result.size());
        CardResponseDTO cardResponse = result.get(0);
        assertEquals("Card 1", cardResponse.getName());
        assertEquals("DIAMOND_1", cardResponse.getRarity());
        assertEquals("Pokemon", cardResponse.getCategory()); 
    }

    @Test
    void testGetTradableCards() {
        when(cardRepository.findByIsInterchangeableTrue()).thenReturn(List.of(card));

        List<CardResponseDTO> result = cardService.getTradableCards();

        assertNotNull(result);
        assertEquals(1, result.size());
        CardResponseDTO cardResponse = result.get(0);
        assertEquals("Card 1", cardResponse.getName());
        assertEquals("DIAMOND_1", cardResponse.getRarity());
        assertEquals("Pokemon", cardResponse.getCategory());  
    }
}
