package com.transit.domain;

public class StandardProfile extends PassengerProfile {
    public StandardProfile(String name, String surname, String tcKimlik, String phoneNumber) {
        super("Standard", name, surname, tcKimlik, phoneNumber);
    }

    @Override
    public double getDiscountRate() {
        return 0.0;
    }
}