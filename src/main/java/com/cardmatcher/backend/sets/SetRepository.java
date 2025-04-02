package com.cardmatcher.backend.sets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetRepository extends JpaRepository<Set, String> {

    Set findByName(String name);  
    
}