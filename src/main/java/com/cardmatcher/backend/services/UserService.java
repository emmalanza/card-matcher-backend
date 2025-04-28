package com.cardmatcher.backend.services;

import com.cardmatcher.backend.models.User;
import com.cardmatcher.backend.models.dtos.cardLists.CardListsDTO;
import com.cardmatcher.backend.models.dtos.cards.CardDTO;
import com.cardmatcher.backend.models.dtos.users.UserDTO;
import com.cardmatcher.backend.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserDTO getCurrentUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        
        List<CardListsDTO> cardListsDTOs = user.getCardLists().stream()
        .map(CardListsDTO::fromCardList)
        .collect(Collectors.toList());

        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.isActive(), user.getPlayerId(), cardListsDTOs);
    }

        public List<CardListsDTO> getCardLists(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return user.getCardLists().stream()
                .map(cardList -> new CardListsDTO(
                        cardList.getId(),
                        cardList.getListType().name(),
                        cardList.getCards().stream()
                                .map(card -> CardDTO.fromCard(card))  
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }
}
