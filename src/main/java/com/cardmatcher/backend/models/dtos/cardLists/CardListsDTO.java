package com.cardmatcher.backend.models.dtos.cardLists;

import com.cardmatcher.backend.models.CardList;
import com.cardmatcher.backend.models.dtos.cards.CardDTO;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CardListsDTO {
    private Long id;
    private String listType;
    private List<CardDTO> cards; 

    public CardListsDTO(Long id, String listType, List<CardDTO> cards) {
        this.id = id;
        this.listType = listType;
        this.cards = cards;
    }

    public static CardListsDTO fromCardList(CardList cardList) {
        List<CardDTO> cardDTOs = cardList.getCards().stream()
                .map(CardDTO::fromCard)
                .collect(Collectors.toList());

        return new CardListsDTO(cardList.getId(), cardList.getListType().toString(), cardDTOs);
    }
}
