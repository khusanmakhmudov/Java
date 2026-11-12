package com.transit.domain;

public class SeniorProfile extends PassengerProfile {
    public SeniorProfile(String name, String surname, String tcKimlik, String phoneNumber) {
    			super("Senior", name, surname, tcKimlik, phoneNumber);
    }
    @Override
    public double getDiscountRate() {
        return 1.0; // 100% discount (Free)
    }
}