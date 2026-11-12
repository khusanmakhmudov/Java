package com.transit.infrastructure;

import com.transit.domain.Transaction;
import java.util.List;

public interface ITransactionRepository {
    void save(Transaction tx);
    
    // For internal system calculations (if ever needed)
    List<Transaction> findByCardId(String cardId); 
    
    // For the UI to prevent memory crashes (Scalable approach)
    List<Transaction> findRecentByCardId(String cardId, int limit); 
}