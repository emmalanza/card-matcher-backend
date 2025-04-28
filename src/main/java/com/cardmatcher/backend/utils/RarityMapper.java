package com.cardmatcher.backend.utils;

import com.cardmatcher.backend.models.Card;

import java.util.Map;

public class RarityMapper {

    private static final Map<String, Card.Rarity> RARITY_MAP = Map.ofEntries(
        Map.entry("One Diamond", Card.Rarity.DIAMOND_1), // 0
        Map.entry("Two Diamond", Card.Rarity.DIAMOND_2), // 1
        Map.entry("Three Diamond", Card.Rarity.DIAMOND_3), // 2
        Map.entry("Four Diamond", Card.Rarity.DIAMOND_4), // 3
        Map.entry("One Star", Card.Rarity.STAR_1), // 4
        Map.entry("Two Star", Card.Rarity.STAR_2), // 5
        Map.entry("Three Star", Card.Rarity.STAR_3), // 6
        Map.entry("One Shiny", Card.Rarity.SHINY_1), // 7
        Map.entry("Two Shiny", Card.Rarity.SHINY_2), // 8
        Map.entry("Crown", Card.Rarity.CROWN), // 9
        Map.entry("None", Card.Rarity.PROMO) // 10
    );

    public static Card.Rarity mapRarity(String rarityString) {
        return RARITY_MAP.getOrDefault(rarityString, null);
    }
}