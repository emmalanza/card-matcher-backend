package com.cardmatcher.backend.services;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.cardmatcher.backend.models.Set;
import com.cardmatcher.backend.models.dtos.SetDTO;
import com.cardmatcher.backend.repositories.SetRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SetService {

    private final SetRepository setRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper; 
    private final String BASE_URL = "https://api.tcgdex.net/v2/en/sets/";

    public SetService(SetRepository setRepository) {
        this.setRepository = setRepository;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper(); 
    }

    public void loadSetsFromAPI() {
        String[] setIds = { "A2", "A1", "A1a", "A2a", "P-A" };

        for (String setId : setIds) {
            String url = BASE_URL + setId;
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            String rawJsonResponse = responseEntity.getBody();
            System.out.println("Raw response from API: " + rawJsonResponse);

            try {
                SetDTO setResponse = objectMapper.readValue(rawJsonResponse, SetDTO.class);

                if (setResponse != null) {
                    if (setRepository.findByName(setResponse.getName()) == null) {
                        Set set = new Set();
                        set.setName(setResponse.getName());
                        set.setId(setResponse.getId());
                        set.setNumOfTotalCards(setResponse.getNumOfTotalCards());
                        set.setReleaseDate(LocalDate.parse(setResponse.getReleaseDate(), DateTimeFormatter.ISO_DATE));

                        setRepository.save(set);
                        System.out.println("Set loaded: " + setResponse.getName());
                    } else {
                        System.out.println("Set " + setResponse.getName() + " already exists.");
                    }
                } else {
                    System.out.println("Err loading set with ID: " + setId);
                }
            } catch (Exception e) {
                System.err.println("Err processing set with ID: " + setId);
                e.printStackTrace();
            }
        }
    }
}