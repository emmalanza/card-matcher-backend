package com.cardmatcher.backend.cards;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, String> {
    
    Card findByName(String name);

    Optional<Card> findById(String id);  

    List<Card> findBySetId(String set);
}