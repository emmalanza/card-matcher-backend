package com.cardmatcher.backend.models.dtos;

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
}
