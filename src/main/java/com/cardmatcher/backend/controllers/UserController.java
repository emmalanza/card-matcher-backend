package com.cardmatcher.backend.controllers;

import com.cardmatcher.backend.models.dtos.ApiResponseDTO;
import com.cardmatcher.backend.models.dtos.cardLists.CardListsDTO;
import com.cardmatcher.backend.models.dtos.users.UserDTO;
import com.cardmatcher.backend.services.UserService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponseDTO<UserDTO>> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            ApiResponseDTO<UserDTO> response = new ApiResponseDTO<>(
                null, "Usuario no autenticado", HttpStatus.UNAUTHORIZED.value());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        UserDTO userDTO = userService.getCurrentUser(userDetails.getUsername());

        ApiResponseDTO<UserDTO> response = new ApiResponseDTO<>(userDTO, "Usuario encontrado", HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/cardlists")   
    public ResponseEntity<ApiResponseDTO<List<CardListsDTO>>> getUserCardLists(@AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponseDTO<>(null, "Usuario no autenticado", HttpStatus.UNAUTHORIZED.value()));
        }

        String username = userDetails.getUsername();

        List<CardListsDTO> cardListsDTOs = userService.getCardLists(username);

        ApiResponseDTO<List<CardListsDTO>> response = new ApiResponseDTO<>(cardListsDTOs, "Listas de cartas encontradas", HttpStatus.OK.value());

        return ResponseEntity.ok(response);
    }
}
