package com.transit.domain;

public interface CardState {
    // Returns true if the tap is allowed, false if blocked
    boolean handleTap(SmartCard card);
    
    void suspend(SmartCard card);
    void activate(SmartCard card);
}