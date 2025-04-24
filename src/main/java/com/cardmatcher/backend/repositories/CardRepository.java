package com.cardmatcher.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cardmatcher.backend.models.Card;

public interface CardRepository extends JpaRepository<Card, String> {
    
    Card findByName(String name);

    Optional<Card> findById(String id);  

    List<Card> findBySetId(String set);

    List<Card> findByIsInterchangeableTrue();
}