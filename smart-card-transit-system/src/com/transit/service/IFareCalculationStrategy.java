package com.transit.service;

import com.transit.domain.PassengerProfile;
import com.transit.domain.TransitModality;

public interface IFareCalculationStrategy {
    double calculateFare(PassengerProfile profile, TransitModality modality);
}