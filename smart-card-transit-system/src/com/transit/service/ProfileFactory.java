package com.transit.service;

import com.transit.domain.*;

public class ProfileFactory {
    
    public static PassengerProfile createProfile(String type, String name, String surname, String tcKimlik, String phoneNumber) {
        if (type == null) return new StandardProfile(name, surname, tcKimlik, phoneNumber);
        
        switch (type.trim().toUpperCase()) {
            case "STUDENT": return new StudentProfile(name, surname, tcKimlik, phoneNumber);
            case "SENIOR": return new SeniorProfile(name, surname, tcKimlik, phoneNumber);
            case "STANDARD": default: return new StandardProfile(name, surname, tcKimlik, phoneNumber);
        }
    }
}