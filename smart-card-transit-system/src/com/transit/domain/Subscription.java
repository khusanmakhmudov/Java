package com.transit.domain;

import java.time.LocalDateTime;

public class Subscription {
    private double remainingCredits;
    private final LocalDateTime expiryDate;

    public Subscription(double initialCredits, int daysValid) {
        this.remainingCredits = initialCredits;
        this.expiryDate = LocalDateTime.now().plusDays(daysValid);
    }

    public boolean isValid() {
        return LocalDateTime.now().isBefore(expiryDate) && remainingCredits > 0;
    }

    public double getRemainingCredits() {
        return remainingCredits;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public boolean consumeCredits(double creditsRequired) {
        if (isValid() && this.remainingCredits >= creditsRequired) {
            this.remainingCredits -= creditsRequired;
            return true;
        }
        return false;
    }
}