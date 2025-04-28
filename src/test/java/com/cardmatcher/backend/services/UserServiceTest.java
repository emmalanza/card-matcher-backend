package com.cardmatcher.backend.services;

import com.cardmatcher.backend.models.User;
import com.cardmatcher.backend.models.dtos.cardLists.CardListsDTO;
import com.cardmatcher.backend.models.dtos.users.UserDTO;
import com.cardmatcher.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setEmail("user@example.com");
        user.setActive(true);
        user.setPlayerId("player1");

        user.setCardLists(new ArrayList<>());
    }

    @Test
    void testGetCurrentUser() {
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));

        UserDTO result = userService.getCurrentUser("username");

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("username", result.getUsername());
        assertEquals("user@example.com", result.getEmail());
        assertTrue(result.isActive());
        assertEquals("player1", result.getPlayerId());
        assertTrue(result.getCardLists().isEmpty());  
    }

    @Test
    void testGetCardLists() {
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));

        List<CardListsDTO> result = userService.getCardLists("username");

        assertNotNull(result); 
    }

    @Test
    void testGetCurrentUser_UserNotFound() {
        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.getCurrentUser("username");
        });
    }

    @Test
    void testGetCardLists_UserNotFound() {
        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.getCardLists("username");
        });
    }
}
