package com.cardmatcher.backend.repositories;

import com.cardmatcher.backend.models.CardList;
import com.cardmatcher.backend.models.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CardListRepository extends JpaRepository<CardList, Long> {
    List<CardList> findByUser(User user);
    CardList findByUserAndListType(User user, CardList.ListType listType);
}
