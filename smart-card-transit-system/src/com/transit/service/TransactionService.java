package com.transit.service;

import com.transit.domain.SmartCard;
import com.transit.domain.Transaction;
import com.transit.domain.TransactionType;
import com.transit.domain.TransitModality;
import com.transit.infrastructure.ICardRepository;
import com.transit.infrastructure.ITransactionRepository;

public class TransactionService {
    private final ICardRepository cardRepository;
    private final ITransactionRepository transactionRepository;
    private final IFareCalculationStrategy standardFareStrategy;

    public TransactionService(ICardRepository cardRepository, ITransactionRepository transactionRepository) {
        this.cardRepository = cardRepository;
        this.transactionRepository = transactionRepository;
        // Injecting stateless singleton strategy to save memory
        this.standardFareStrategy = new StandardFareStrategy();
    }

 // REPLACE YOUR processTapIn method in TransactionService.java with this:
    public synchronized boolean processTapIn(String cardId, TransitModality modality) {
        SmartCard card = cardRepository.findById(cardId);
        if (card == null || !card.canTap()) return false;

        // 1. Try to use Subscription Credits First
        if (card.hasActiveSubscription()) {
            double creditCost = 0;
            switch (modality) {
                case BUS: creditCost = 1.0; break;
                case RAILWAY: creditCost = 2.0; break;
                case FERRY: creditCost = 3.0; break;
            }

            if (card.getSubscription().consumeCredits(creditCost)) {
                cardRepository.save(card);
                // Record transaction with 0.0 TL cost because it used credits
                Transaction tx = new Transaction(java.util.UUID.randomUUID().toString(), cardId, 0.0, modality, TransactionType.TAP_IN);
                transactionRepository.save(tx);
                
                System.out.printf("\n[ACCESS GRANTED] Used Pass | Cost: %.1f Credits | Remaining: %.1f Credits\n", 
                        creditCost, card.getSubscription().getRemainingCredits());
                return true;
            }
        }

        // 2. Fallback to Standard Wallet Balance if no subscription or out of credits
        double fareAmount = standardFareStrategy.calculateFare(card.getProfile(), modality);
        if (card.deductBalance(fareAmount)) {
            cardRepository.save(card);
            Transaction tx = new Transaction(java.util.UUID.randomUUID().toString(), cardId, fareAmount, modality, TransactionType.TAP_IN);
            transactionRepository.save(tx);
            
            System.out.printf("\n[ACCESS GRANTED] Paid via Wallet | Fare: %.2f TL | Balance: %.2f TL\n", fareAmount, card.getBalance());
            return true;
        } else {
            System.out.println("\n[ACCESS DENIED] Insufficient Balance and no active pass.");
            return false;
        }
    }
 // NEW: Retrieve transaction history safely
    public java.util.List<Transaction> getRecentTransactions(String cardId, int limit) {
        return transactionRepository.findRecentByCardId(cardId, limit);
    }
}