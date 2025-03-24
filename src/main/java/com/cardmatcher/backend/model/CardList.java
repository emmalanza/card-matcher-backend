package com.cardmatcher.backend.model;

import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity(name = "cards_lists")
@Table(name = "cards_lists")
@Data
public class CardList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con el usuario
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Una lista de cartas
    @ManyToMany
    @JoinTable(
        name = "cards_in_lists",
        joinColumns = @JoinColumn(name = "card_list_id"),
        inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private List<Card> cards;

    @Enumerated
    @Column(nullable = false)
    private ListType listType;

    // Enum para los tipos de lista (wanted o offered)
    public enum ListType {
        WANTED,  // Cartas que el usuario quiere obtener
        OFFERED    // Cartas que el usuario está dispuesto a intercambiar
    }
}
