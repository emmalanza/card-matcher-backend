package com.cardmatcher.backend.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity(name ="users")
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // Una lista para las cartas que desea obtener
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private CardList wantedList;

    // Una lista para las cartas que está dispuesto a intercambiar
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private CardList offeredList;

    @ManyToMany
    @JoinTable(
        name = "user_fav_cards",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private List<Card> favoriteCards;
}
