package com.cardmatcher.backend.repositories;

import com.cardmatcher.backend.models.CardList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardListRepository extends JpaRepository<CardList, Long> {
}
