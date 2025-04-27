package com.cardmatcher.backend.models.dtos.cards;

import com.cardmatcher.backend.models.Card;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardDTO {

    private String id;

    private String name;

    @JsonProperty("image")
    private String imgUrl;

    private String category;

    private String rarity;

    public static CardDTO fromCard(Card card) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(card.getId());
        cardDTO.setName(card.getName());
        cardDTO.setImgUrl(card.getImgUrl()); 
        cardDTO.setCategory(card.getCategory());
        cardDTO.setRarity(card.getRarity().toString());
        return cardDTO;
    }
}
