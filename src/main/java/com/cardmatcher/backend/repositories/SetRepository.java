package com.cardmatcher.backend.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cardmatcher.backend.models.Set;

public interface SetRepository extends JpaRepository<Set, String> {
    Set findByName(String name);  
}