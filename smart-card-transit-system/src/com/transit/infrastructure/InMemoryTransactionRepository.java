package com.transit.infrastructure;

import com.transit.domain.Transaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTransactionRepository implements ITransactionRepository {
    
    private final Map<String, List<Transaction>> dataStore;

    public InMemoryTransactionRepository() {
        this.dataStore = new HashMap<>();
    }

    @Override
    public void save(Transaction tx) {
        // O(1) instantaneous save
        dataStore.computeIfAbsent(tx.getCardId(), k -> new ArrayList<>()).add(tx);
    }

    @Override
    public List<Transaction> findByCardId(String cardId) {
        return dataStore.getOrDefault(cardId, new ArrayList<>());
    }

    @Override
    public List<Transaction> findRecentByCardId(String cardId, int limit) {
        // O(1) lookup
        List<Transaction> allTransactions = dataStore.getOrDefault(cardId, new ArrayList<>());
        
        int size = allTransactions.size();
        
        // If they have fewer transactions than the limit, return them all safely
        if (size <= limit) {
            return new ArrayList<>(allTransactions);
        }
        
        // Otherwise, only extract the most recent 'N' transactions to save memory
        return new ArrayList<>(allTransactions.subList(size - limit, size));
    }
}