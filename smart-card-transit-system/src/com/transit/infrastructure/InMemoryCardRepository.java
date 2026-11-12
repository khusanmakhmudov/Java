package com.transit.infrastructure;

import com.transit.domain.SmartCard;
import java.util.HashMap;
import java.util.Map;

public class InMemoryCardRepository implements ICardRepository {
    // Encapsulated data store simulating a database table
    private final Map<String, SmartCard> dataStore;

    public InMemoryCardRepository() {
        this.dataStore = new HashMap<>();
    }

    @Override
    public void save(SmartCard card) {
        // In a Map, put() handles both Insert (new) and Update (existing)
        dataStore.put(card.getCardId(), card);
    }

    @Override
    public SmartCard findById(String cardId) {
        return dataStore.get(cardId);
    }
}