package com.cardmatcher.backend.models.dtos.users;

import lombok.Data;

import java.util.List;

import com.cardmatcher.backend.models.dtos.cardLists.CardListsDTO;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private boolean isActive;
    private String playerId;
    private List<CardListsDTO> cardLists; 

    public UserDTO(Long id, String username, String email, boolean isActive, 
    String playerId, List<CardListsDTO> cardLists) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.isActive = isActive;
        this.playerId = playerId;
        this.cardLists = cardLists;
    }
}
