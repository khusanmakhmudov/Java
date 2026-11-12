package com.transit.service;

import com.transit.domain.PassengerProfile;
import com.transit.domain.SmartCard;
import com.transit.infrastructure.ICardRepository;
import com.transit.infrastructure.SecurityUtil;
import java.util.Random;

public class CardManagementService {
    private final ICardRepository cardRepository;
    private final Random random;

    public CardManagementService(ICardRepository cardRepository) {
        this.cardRepository = cardRepository;
        this.random = new Random();
    }

    private String generateUniqueCardId(String profileType) {
        String prefix;
        switch (profileType.trim().toUpperCase()) {
            case "STUDENT": prefix = "21"; break;
            case "SENIOR": prefix = "32"; break;
            case "STANDARD": default: prefix = "10"; break;
        }

        String generatedId;
        do {
            int randomPart = random.nextInt(1000000000); 
            generatedId = prefix + String.format("%09d", randomPart);
        } while (cardRepository.findById(generatedId) != null); 

        return generatedId;
    }

    // UPDATED: Now requires a plainPin parameter
    public SmartCard issueNewCard(String profileType, String name, String surname, String tcKimlik, String phoneNumber, String plainPin) {
        if (profileType.equalsIgnoreCase("STUDENT") && tcKimlik.length() != 11) {
            throw new IllegalArgumentException("Verification Failed: Invalid TC Kimlik for Student Profile.");
        }
        
        // Validate PIN format (Must be exactly 6 digits)
        if (plainPin == null || !plainPin.matches("\\d{6}")) {
            throw new IllegalArgumentException("Security Error: PIN must be exactly 6 digits.");
        }

        String uniqueCardId = generateUniqueCardId(profileType);
        PassengerProfile profile = ProfileFactory.createProfile(profileType, name, surname, tcKimlik, phoneNumber);
        
        // Hash the PIN before saving it to the Domain Entity
        String hashedPin = SecurityUtil.getInstance().hashPin(plainPin);
        
        SmartCard newCard = new SmartCard(uniqueCardId, profile, hashedPin);
        cardRepository.save(newCard);
        
        return newCard;
    }

    // NEW: Secure Login Service
    public SmartCard authenticateUser(String cardId, String plainPin) {
        SmartCard card = cardRepository.findById(cardId);
        if (card != null) {
            String hashedInput = SecurityUtil.getInstance().hashPin(plainPin);
            if (card.verifyPin(hashedInput)) {
                return card; // Login Success
            }
        }
        return null; // Login Failed (Wrong ID or PIN)
    }

    public SmartCard getCardInfo(String cardId) {
        return cardRepository.findById(cardId);
    }
}