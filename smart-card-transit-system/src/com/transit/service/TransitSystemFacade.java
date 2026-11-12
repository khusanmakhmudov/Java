package com.transit.service;

import com.transit.domain.SmartCard;
import com.transit.domain.Transaction;
import com.transit.domain.TransitModality;
import java.util.List;

public class TransitSystemFacade {
    private final CardManagementService cardService;
    private final WalletService walletService;
    private final TransactionService transactionService;

    public TransitSystemFacade(CardManagementService cardService, WalletService walletService, TransactionService transactionService) {
        this.cardService = cardService;
        this.walletService = walletService;
        this.transactionService = transactionService;
    }

    public void requestNewCard(String type, String name, String surname, String tcKimlik, String phone, String pin) {
        try {
            SmartCard generatedCard = cardService.issueNewCard(type, name, surname, tcKimlik, phone, pin);
            System.out.println("\n✅ SUCCESS: Card created and activated!");
            System.out.println("💳 YOUR NEW CARD NUMBER IS: " + generatedCard.getCardId());
            System.out.println("⚠️ Please write this number down. You will need it to log in.");
        } catch (IllegalArgumentException e) {
            System.out.println("\n❌ ERROR: " + e.getMessage());
        }
    }

    // NEW: Secure Login Delegation
    public SmartCard login(String cardId, String plainPin) {
        return cardService.authenticateUser(cardId, plainPin);
    }

    public void topUpWallet(String cardId, double amount) {
        walletService.topUp(cardId, amount);
    }
    public void buyMonthlyPass(String cardId) {
        walletService.buyMonthlyPass(cardId);
    }
    public void tapCardForRide(String cardId, TransitModality modality) {
        transactionService.processTapIn(cardId, modality);
    }

    public void checkCardBalance(String cardId) {
        SmartCard card = cardService.getCardInfo(cardId);
        if (card != null) {
            System.out.printf(" 💰 Wallet Balance : %.2f TL\n", card.getBalance());
            if (card.hasActiveSubscription()) {
                System.out.printf(" 🎫 Monthly Pass   : ACTIVE (%.1f Credits Remaining)\n", 
                        card.getSubscription().getRemainingCredits());
            } else {
                System.out.println(" 🎫 Monthly Pass   : INACTIVE");
            }
        }
    }

    // NEW: Formatted Transaction History Table
    public void printTransactionHistory(String cardId) {
        List<Transaction> history = transactionService.getRecentTransactions(cardId, 10); // Limit to last 10
        
        if (history.isEmpty()) {
            System.out.println("\n📭 No recent transactions found for this card.");
            return;
        }

        System.out.println("\n=== 📜 RECENT TRANSACTION HISTORY ===");
        System.out.printf("%-20s | %-10s | %-10s | %-10s\n", "Date & Time", "Type", "Amount", "Transit Mode");
        System.out.println("------------------------------------------------------------------");
        
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Transaction tx : history) {
            String modeStr = (tx.getType() == com.transit.domain.TransactionType.TOP_UP) ? "N/A" : tx.getDetails().split("Mode: ")[1];
            System.out.printf("%-20s | %-10s | %-6.2f TL  | %-10s\n", 
                    "Today " + formatter.format(java.time.LocalDateTime.now()), // Simulating timestamp for demo
                    tx.getType(), tx.getAmount(), modeStr);
        }
        System.out.println("==================================================================");
    }
}