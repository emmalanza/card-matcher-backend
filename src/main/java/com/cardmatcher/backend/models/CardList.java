package com.cardmatcher.backend.models;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity(name = "cards_lists")
@Table(name = "cards_lists", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "user_id", "listType" })
})
@Data
public class CardList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(name = "cards_in_lists", joinColumns = @JoinColumn(name = "card_list_id"), inverseJoinColumns = @JoinColumn(name = "card_id"))
    private List<Card> cards;

    @Enumerated
    @Column(nullable = false)
    private ListType listType;

    public enum ListType {
        WANTED,
        OFFERED,
        COLLECTION
    }
}
