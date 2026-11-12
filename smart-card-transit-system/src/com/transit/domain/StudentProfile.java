package com.transit.domain;

public class StudentProfile extends PassengerProfile {
    public StudentProfile(String name, String surname, String tcKimlik, String phoneNumber) {
		super("Student", name, surname, tcKimlik, phoneNumber);
	}
	
	
    @Override
    public double getDiscountRate() {
        return 0.50; // 50% discount
    }
}