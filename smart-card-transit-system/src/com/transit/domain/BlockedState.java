package com.transit.domain;

public class BlockedState implements CardState {
    @Override
    public boolean handleTap(SmartCard card) {
        System.out.println("Access Denied: Card is permanently blocked.");
        return false; 
    }

    @Override
    public void suspend(SmartCard card) {
        System.out.println("Cannot suspend a blocked card.");
    }

    @Override
    public void activate(SmartCard card) {
        System.out.println("Cannot activate a blocked card. Please request a new card.");
    }
}