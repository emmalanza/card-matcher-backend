package com.cardmatcher.backend.controllers;

import com.cardmatcher.backend.models.dtos.ApiResponseDTO;
import com.cardmatcher.backend.models.dtos.users.UserDTO;
import com.cardmatcher.backend.models.dtos.users.UserLoginDTO;
import com.cardmatcher.backend.models.dtos.users.UserRegisterDTO;
import com.cardmatcher.backend.services.AuthService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<UserDTO>> registerUser(
            @Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            UserDTO userDTO = authService.registerUser(userRegisterDTO);
            ApiResponseDTO<UserDTO> response = new ApiResponseDTO<>(
                    userDTO, "Usuario registrado exitosamente", HttpStatus.CREATED.value());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponseDTO<UserDTO> response = new ApiResponseDTO<>(
                    null, "Error al registrar el usuario: " + e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<String>> loginUser(
            @Valid @RequestBody UserLoginDTO userLoginDTO) {
        try {
            String token = authService.loginUser(userLoginDTO);
            ApiResponseDTO<String> response = new ApiResponseDTO<>(
                    token, "Inicio de sesión exitoso", HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponseDTO<String> response = new ApiResponseDTO<>(
                    null, "Error al iniciar sesión: " + e.getMessage(), HttpStatus.UNAUTHORIZED.value());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
