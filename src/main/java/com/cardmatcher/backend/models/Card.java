package com.cardmatcher.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity(name = "cards")
@Table(name = "cards")
@Data
public class Card {

    @Id
    @Column(nullable = false, unique = true)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Boolean isInterchangeable;

    @ManyToOne
    @JoinColumn(name = "set_id", nullable = false)
    private Set set;

    @Enumerated
    @Column(nullable = false)
    private Rarity rarity;

    public enum Rarity {
        DIAMOND_1,
        DIAMOND_2,
        DIAMOND_3,
        DIAMOND_4,
        STAR_1,
        STAR_2,
        STAR_3,
        CROWN,
        PROMO 
    }

    public boolean canBeExchanged() {
        return rarity == Rarity.DIAMOND_1 || rarity == Rarity.DIAMOND_2 || rarity == Rarity.DIAMOND_3 || rarity == Rarity.DIAMOND_4 || rarity == Rarity.STAR_1;
    }

    public void setIsInterchangeable() {
        this.isInterchangeable = canBeExchanged();
    }
}
