package com.cardmatcher.backend.services;

import com.cardmatcher.backend.models.Set;
import com.cardmatcher.backend.models.dtos.sets.SetDTO;
import com.cardmatcher.backend.repositories.SetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SetServiceTest {

    @Mock
    private SetRepository setRepository;

    @InjectMocks
    private SetService setService;

    private Set set;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        set = new Set();
        set.setId("set1");
        set.setName("Set 1");
        set.setReleaseDate(java.time.LocalDate.parse("2025-01-01"));
        set.setNumOfTotalCards(100);
    }

    @Test
    void testGetAllSets() {
        List<Set> sets = new ArrayList<>();
        sets.add(set);
        when(setRepository.findAll()).thenReturn(sets);

        List<SetDTO> result = setService.getAllSets();

        assertNotNull(result);
        assertEquals(1, result.size());

        SetDTO setDTO = result.get(0);
        assertEquals("Set 1", setDTO.getName());
        assertEquals("set1", setDTO.getId());
        assertEquals("2025-01-01", setDTO.getReleaseDate());
        assertEquals(100, setDTO.getNumOfTotalCards());
    }

    @Test
    void testGetAllSets_EmptyList() {
        when(setRepository.findAll()).thenReturn(new ArrayList<>());

        List<SetDTO> result = setService.getAllSets();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
