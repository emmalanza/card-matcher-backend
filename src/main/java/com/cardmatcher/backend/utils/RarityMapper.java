package com.cardmatcher.backend.utils;

import com.cardmatcher.backend.models.Card;

import java.util.Map;

public class RarityMapper {

    private static final Map<String, Card.Rarity> RARITY_MAP = Map.of(
        "One Diamond", Card.Rarity.DIAMOND_1,
        "Two Diamond", Card.Rarity.DIAMOND_2,
        "Three Diamond", Card.Rarity.DIAMOND_3,
        "Four Diamond", Card.Rarity.DIAMOND_4,
        "One Star", Card.Rarity.STAR_1,
        "Two Star", Card.Rarity.STAR_2,
        "Three Star", Card.Rarity.STAR_3,
        "Crown", Card.Rarity.CROWN,
        "None", Card.Rarity.PROMO
    );

    public static Card.Rarity mapRarity(String rarityString) {
        return RARITY_MAP.getOrDefault(rarityString, null); 
    }
}