package com.cardmatcher.backend.controllers;

import com.cardmatcher.backend.models.dtos.ApiResponseDTO;
import com.cardmatcher.backend.models.dtos.sets.SetDTO;
import com.cardmatcher.backend.services.SetService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sets")
public class SetController {

    private final SetService setService;

    @Autowired
    public SetController(SetService setService) {
        this.setService = setService;
    }

    @GetMapping("/load-sets")
    public String loadSets() {
        setService.loadSetsFromAPI();
        return "Sets cargados exitosamente.";
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponseDTO<List<SetDTO>>> getAllSets() {
        try {

            List<SetDTO> sets = setService.getAllSets(); 

            if (sets.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponseDTO<>(
                    null, "No hay sets disponibles.", HttpStatus.NO_CONTENT.value()));
            }

            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(
                sets, "Sets obtenidos correctamente.", HttpStatus.OK.value()));

        } catch (Exception e) {
            ApiResponseDTO<List<SetDTO>> response = new ApiResponseDTO<>(
                    null, "Error al obtener los sets: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
