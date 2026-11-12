package com.transit.service;

import com.transit.domain.SmartCard;
import com.transit.domain.Transaction;
import com.transit.domain.TransactionType;
import com.transit.infrastructure.ICardRepository;
import com.transit.infrastructure.ITransactionRepository;

public class WalletService {
    private final ICardRepository cardRepository;
    private final ITransactionRepository transactionRepository;
    public static final double MONTHLY_PASS_PRICE = 740.0;
    public static final double MONTHLY_PASS_CREDITS = 100.0;
    public static final int MONTHLY_PASS_DAYS = 30;

    public WalletService(ICardRepository cardRepository, ITransactionRepository transactionRepository) {
        this.cardRepository = cardRepository;
        this.transactionRepository = transactionRepository;
    }

    // NFR: Using synchronized ensures thread-safe top-ups during concurrent web/mobile API calls
    public synchronized boolean topUp(String cardId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Top-up amount must be greater than zero.");
        }

        SmartCard card = cardRepository.findById(cardId);
        if (card == null) {
            System.out.println("Error: Card not found.");
            return false;
        }

        if (!card.addBalance(amount)) {
            System.out.printf("Top-up failed: Cannot exceed maximum card balance of %.2f TL.\n", SmartCard.MAX_BALANCE);
            return false;
        }
        cardRepository.save(card);

        // Record the historical event
        Transaction tx = new Transaction(java.util.UUID.randomUUID().toString(), cardId, amount, null, TransactionType.TOP_UP);
        transactionRepository.save(tx);
        
        System.out.printf("Successfully added %.2f TL. New Balance: %.2f TL\n", amount, card.getBalance());
        return true;
    }
    public synchronized boolean buyMonthlyPass(String cardId) {
        SmartCard card = cardRepository.findById(cardId);
        if (card == null) return false;

        // NEW DOMAIN CONSTRAINT: Only Students can buy the pass
        if (!card.getProfile().getTypeName().equalsIgnoreCase("STUDENT")) {
            System.out.println("   [!] Subscription Denied: Monthly passes are exclusive to Student profiles.");
            return false;
        }

        if (card.hasActiveSubscription()) {
            System.out.println("   [!] You already have an active monthly pass.");
            return false;
        }

        // Try to deduct the 740 TL from their balance
        if (card.deductBalance(MONTHLY_PASS_PRICE)) {
            card.activateSubscription(MONTHLY_PASS_CREDITS, MONTHLY_PASS_DAYS);
            cardRepository.save(card);

            Transaction tx = new Transaction(java.util.UUID.randomUUID().toString(), 
                    cardId, MONTHLY_PASS_PRICE, null, TransactionType.BUY_PASS);
            transactionRepository.save(tx);
            
            System.out.println("   [SUCCESS] Student Monthly Pass activated! 100 Credits added.");
            return true;
        } else {
            System.out.printf("   [!] Insufficient funds. You need %.2f TL.\n", MONTHLY_PASS_PRICE);
            return false;
        }
    }
}