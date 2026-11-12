package com.transit.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final String transactionId;
    private final String cardId;
    private final LocalDateTime timestamp;
    private final double amount;
    private final TransitModality modality;
    private final TransactionType type;

    public Transaction(String transactionId, String cardId, double amount, TransitModality modality, TransactionType type) {
        this.transactionId = transactionId;
        this.cardId = cardId;
        this.timestamp = LocalDateTime.now();
        this.amount = amount;
        this.modality = modality;
        this.type = type;
    }

    public String getCardId() { return cardId; }
    public double getAmount() { return amount; }
    public TransactionType getType() { return type; }

    public String getDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %s | Card: %s | Amount: %.2f TL | Mode: %s", 
                timestamp.format(formatter), type, cardId, amount, 
                (modality != null ? modality : "N/A"));
    }
}