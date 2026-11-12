package com.transit.domain;

public abstract class PassengerProfile {
    private final String typeName;
    private final String name;
    private final String surname;
    private final String tcKimlik;
    private final String phoneNumber; 

    public PassengerProfile(String typeName, String name, String surname, String tcKimlik, String phoneNumber) {
        this.typeName = typeName;
        this.name = name;
        this.surname = surname;
        this.tcKimlik = tcKimlik;
        this.phoneNumber = formatAndValidatePhone(phoneNumber); 
    }

    // Phone Number Regex and Formatting Logic
    private String formatAndValidatePhone(String phone) {
        if (phone == null) {
            throw new IllegalArgumentException("Phone number cannot be null.");
        }
        
        // Remove all spaces for easier validation
        String cleaned = phone.replaceAll("\\s+", "");
        
        // Regex: Matches optional +90 or 0, followed by exactly 5 and 9 more digits
        if (!cleaned.matches("^(?:\\+90|0)?(5\\d{9})$")) {
            throw new IllegalArgumentException("Invalid Turkish phone number format. Must be a valid 5XX number.");
        }
        
        // Extract the last 10 digits (the 5XX...) and prepend +90
        return "+90" + cleaned.substring(cleaned.length() - 10);
    }

    // Getters
    public String getTypeName() { return typeName; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getTcKimlik() { return tcKimlik; }
    public String getPhoneNumber() { return phoneNumber; }

    public abstract double getDiscountRate(); 
}