package com.cardmatcher.backend.services;

import com.cardmatcher.backend.models.CardList;
import com.cardmatcher.backend.models.Card;
import com.cardmatcher.backend.models.dtos.cards.CardDTO;
import com.cardmatcher.backend.repositories.CardListRepository;
import com.cardmatcher.backend.repositories.CardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardListService {

    private final CardListRepository cardListRepository;
    private final CardRepository cardRepository;

    @Autowired
    public CardListService(CardListRepository cardListRepository, CardRepository cardRepository) {
        this.cardListRepository = cardListRepository;
        this.cardRepository = cardRepository;

    }


    public CardDTO addCardToCardList(Long cardListId, String cardId) throws Exception {
        CardList cardList = cardListRepository.findById(cardListId)
                .orElseThrow(() -> new Exception("Lista de cartas no encontrada"));
        
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new Exception("Carta no encontrada"));

        cardList.getCards().add(card);
        cardListRepository.save(cardList);

        new CardDTO();
        return CardDTO.fromCard(card);
    }

    public CardDTO removeCardFromCardList(Long cardListId, String cardId) throws Exception {
        CardList cardList = cardListRepository.findById(cardListId)
                .orElseThrow(() -> new Exception("Lista de cartas no encontrada"));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new Exception("Carta no encontrada"));

        cardList.getCards().remove(card);
        cardListRepository.save(cardList);


        new CardDTO();
        return CardDTO.fromCard(card);
    }

}
