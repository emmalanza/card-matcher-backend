package com.cardmatcher.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardmatcher.backend.models.Card;
import com.cardmatcher.backend.models.CardList;
import com.cardmatcher.backend.models.User;
import com.cardmatcher.backend.repositories.CardListRepository;
import com.cardmatcher.backend.repositories.CardRepository;

@Service
public class CardListService {

        @Autowired
        private CardListRepository cardListRepository;

        @Autowired
        private CardRepository cardRepository;

        public void addCardToCardList(Long cardListId, String cardId) {
                CardList cardList = cardListRepository.findById(cardListId)
                                .orElseThrow(() -> new RuntimeException("Lista no encontrada"));
                Card card = cardRepository.findById(cardId)
                                .orElseThrow(() -> new RuntimeException("Carta no encontrada"));

                cardList.getCards().add(card);
                cardListRepository.save(cardList);
        }

        public void removeCardFromCardList(Long cardListId, String cardId) {
                CardList cardList = cardListRepository.findById(cardListId)
                                .orElseThrow(() -> new RuntimeException("Lista no encontrada"));
                Card card = cardRepository.findById(cardId)
                                .orElseThrow(() -> new RuntimeException("Carta no encontrada"));

                cardList.getCards().remove(card);
                cardListRepository.save(cardList);
        }

        public List<CardList> getCardListsByUser(User user) {
                return cardListRepository.findByUser(user);
        }
}