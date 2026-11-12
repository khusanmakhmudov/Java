package com.transit.service;

import com.transit.domain.PassengerProfile;
import com.transit.domain.TransitModality;

public class StandardFareStrategy implements IFareCalculationStrategy {
    // Base fares could easily be loaded from a config file later
    private static final double BUS_FARE = 15.0;
    private static final double FERRY_FARE = 20.0;
    private static final double METRO_FARE = 12.0;

    @Override
    public double calculateFare(PassengerProfile profile, TransitModality modality) {
        double baseFare;
        switch (modality) {
            case FERRY: baseFare = FERRY_FARE; break;
            case RAILWAY: baseFare = METRO_FARE; break;
            case BUS: default: baseFare = BUS_FARE; break;
        }
        
        // Apply the discount polymorphism defined in the Domain layer
        return baseFare * (1.0 - profile.getDiscountRate());
    }
}