package com.transit.domain;

public class ActiveState implements CardState {
    @Override
    public boolean handleTap(SmartCard card) {
        // An active card is allowed to process a transit tap
        return true; 
    }

    @Override
    public void suspend(SmartCard card) {
        System.out.println("Card is now suspended.");
        card.setState(new SuspendedState());
    }

    @Override
    public void activate(SmartCard card) {
        System.out.println("Card is already active.");
    }
}