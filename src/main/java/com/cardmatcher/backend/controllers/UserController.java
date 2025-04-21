package com.cardmatcher.backend.controllers;

import com.cardmatcher.backend.models.User;
import com.cardmatcher.backend.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }

        String username = userDetails.getUsername();
        System.out.println("Usuario encontrado: " + username);

        User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


        return ResponseEntity.ok(user);
    }

}
