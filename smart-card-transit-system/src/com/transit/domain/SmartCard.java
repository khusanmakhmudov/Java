package com.transit.domain;

public class SmartCard {
    public static final double MAX_BALANCE = 5000.0;
    
    private final String cardId;
    private double balance;
    private final PassengerProfile profile;
    private CardState currentState;
    
    private Subscription activeSubscription;
    // NEW: Security Credential
    private final String hashedPin;

    // UPDATED CONSTRUCTOR
    public SmartCard(String cardId, PassengerProfile profile, String hashedPin) {
        this.cardId = cardId;
        this.profile = profile;
        this.hashedPin = hashedPin;
        this.balance = 0.0; 
        this.currentState = new ActiveState(); 
    }

    // NEW: Domain Authentication Logic
    public boolean verifyPin(String hashedInput) {
        return this.hashedPin.equals(hashedInput);
    }

    // ... (Keep the rest of your getters and balance methods exactly the same)
    public String getCardId() { return cardId; }
    public double getBalance() { return balance; }
    public PassengerProfile getProfile() { return profile; }
    public CardState getCurrentState() { return currentState; }
    
    protected void setState(CardState state) { this.currentState = state; }
    
    public boolean addBalance(double amount) {
        if (amount > 0 && (this.balance + amount) <= MAX_BALANCE) {
            this.balance += amount;
            return true;
        }
        return false;
    }

    public boolean deductBalance(double amount) {
        if (amount > 0 && this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }


    // ADD THESE METHODS to the bottom of SmartCard.java
    public void activateSubscription(double credits, int daysValid) {
        this.activeSubscription = new Subscription(credits, daysValid);
    }

    public Subscription getSubscription() {
        return activeSubscription;
    }

    public boolean hasActiveSubscription() {
        return activeSubscription != null && activeSubscription.isValid();
    }

    public boolean canTap() { return currentState.handleTap(this); }
    public void suspendCard() { currentState.suspend(this); }
    public void activateCard() { currentState.activate(this); }
    public void blockCard() { this.setState(new BlockedState()); }
}