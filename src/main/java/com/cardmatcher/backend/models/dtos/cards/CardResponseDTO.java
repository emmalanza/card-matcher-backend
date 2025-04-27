package com.cardmatcher.backend.models.dtos.cards;

import lombok.Data;

@Data
public class CardResponseDTO {

    private String id;
    private String name;
    private String imgUrl;
    private String category;
    private String rarity;
    private boolean isInterchangeable;
    private String setName;  
    
}
