package com.cardmatcher.backend.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity(name = "sets")
@Table(name = "sets")
@Data
public class Set {

    @Id
    @Column(nullable = false, unique = true)
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int numOfTotalCards;

    @Column(nullable = false)
    private LocalDate releaseDate;
}
