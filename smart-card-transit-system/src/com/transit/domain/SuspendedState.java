package com.transit.domain;

public class SuspendedState implements CardState {
    @Override
    public boolean handleTap(SmartCard card) {
        // A suspended card is denied transit access
        System.out.println("Access Denied: Card is suspended.");
        return false; 
    }

    @Override
    public void suspend(SmartCard card) {
        System.out.println("Card is already suspended.");
    }

    @Override
    public void activate(SmartCard card) {
        System.out.println("Card is now active.");
        card.setState(new ActiveState());
    }
}