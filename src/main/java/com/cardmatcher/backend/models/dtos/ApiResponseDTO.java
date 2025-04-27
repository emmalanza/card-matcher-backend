package com.cardmatcher.backend.models.dtos;

import lombok.Data;

@Data
public class ApiResponseDTO<T> {
    private T data;
    private String message;
    private int status;

    public ApiResponseDTO(T data, String message, int status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public ApiResponseDTO(String message, int status) {
        this.message = message;
        this.status = status;
    }

}

