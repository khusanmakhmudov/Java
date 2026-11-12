package com.transit.infrastructure;

import java.security.MessageDigest;
import java.util.Base64;

public class SecurityUtil {
    
    // 1. The private static instance of itself
    private static SecurityUtil instance;
    
    // The expensive cryptographic engine we only want to load once
    private final MessageDigest digest;

    // 2. Private constructor prevents anyone else from using 'new SecurityUtil()'
    private SecurityUtil() {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize cryptographic engine.", e);
        }
    }

    // 3. Global access point (Thread-safe)
    public static synchronized SecurityUtil getInstance() {
        if (instance == null) {
            instance = new SecurityUtil();
        }
        return instance;
    }

    // 4. The actual hashing behavior (synchronized to prevent thread collisions on the digest)
    public synchronized String hashPin(String plainPin) {
        try {
            byte[] hash = digest.digest(plainPin.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Failed to hash PIN", e);
        }
    }
}