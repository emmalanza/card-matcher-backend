package com.cardmatcher.backend.models.dtos.sets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SetDTO {

    private String name;

    private String releaseDate;

    private String id;

    private int numOfTotalCards; 

    public SetDTO(@JsonProperty("name") String name,
                  @JsonProperty("id") String id,
                  @JsonProperty("releaseDate") String releaseDate,
                  @JsonProperty("cardCount") Object cardCount) {

        this.name = name;
        this.id = id;
        this.releaseDate = releaseDate;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            @SuppressWarnings("unchecked")
            java.util.Map<String, Object> cardCountMap = objectMapper.convertValue(cardCount, java.util.Map.class);
            this.numOfTotalCards = ((Number) cardCountMap.get("total")).intValue();
        } catch (Exception e) {
            this.numOfTotalCards = 0; 
        }
    }

	public SetDTO() {
	}

}