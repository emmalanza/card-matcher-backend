package com.cardmatcher.backend.services;

import com.cardmatcher.backend.models.Card;
import com.cardmatcher.backend.models.CardList;
import com.cardmatcher.backend.models.dtos.cards.CardDTO;
import com.cardmatcher.backend.repositories.CardListRepository;
import com.cardmatcher.backend.repositories.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardListServiceTest {

    @Mock
    private CardListRepository cardListRepository;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardListService cardListService;

    private CardList cardList;
    private Card card;
    private CardDTO cardDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cardList = new CardList();
        card = new Card();
        card.setId("card1");
        card.setRarity(Card.Rarity.STAR_1); 
        cardList.setId(1L);
        cardList.setCards(new java.util.ArrayList<>());
        cardDTO = new CardDTO();
    }

    @Test
    void testAddCardToCardListSuccess() throws Exception {
        when(cardListRepository.findById(1L)).thenReturn(Optional.of(cardList));
        when(cardRepository.findById("card1")).thenReturn(Optional.of(card));
        
        CardDTO result = cardListService.addCardToCardList(1L, "card1");

        assertNotNull(result);
        assertEquals(card.getId(), result.getId());
        assertEquals(1, cardList.getCards().size());
        verify(cardListRepository, times(1)).save(cardList);
    }

    @Test
    void testAddCardToCardListCardListNotFound() {
        when(cardListRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            cardListService.addCardToCardList(1L, "card1");
        });

        assertEquals("Lista de cartas no encontrada", exception.getMessage());
    }

    @Test
    void testAddCardToCardListCardNotFound() {
        when(cardListRepository.findById(1L)).thenReturn(Optional.of(cardList));
        when(cardRepository.findById("card1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            cardListService.addCardToCardList(1L, "card1");
        });

        assertEquals("Carta no encontrada", exception.getMessage());
    }

    @Test
    void testRemoveCardFromCardListSuccess() throws Exception {
        cardList.getCards().add(card);
        
        when(cardListRepository.findById(1L)).thenReturn(Optional.of(cardList));
        when(cardRepository.findById("card1")).thenReturn(Optional.of(card));

        CardDTO result = cardListService.removeCardFromCardList(1L, "card1");

        assertNotNull(result);
        assertEquals(card.getId(), result.getId());
        assertEquals(0, cardList.getCards().size());
        verify(cardListRepository, times(1)).save(cardList);
    }

    @Test
    void testRemoveCardFromCardListCardListNotFound() {
        when(cardListRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            cardListService.removeCardFromCardList(1L, "card1");
        });

        assertEquals("Lista de cartas no encontrada", exception.getMessage());
    }

    @Test
    void testRemoveCardFromCardListCardNotFound() {
        when(cardListRepository.findById(1L)).thenReturn(Optional.of(cardList));
        when(cardRepository.findById("card1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            cardListService.removeCardFromCardList(1L, "card1");
        });

        assertEquals("Carta no encontrada", exception.getMessage());
    }
}
