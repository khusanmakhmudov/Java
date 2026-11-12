package com.transit.presentation;

import com.transit.domain.SmartCard;
import com.transit.domain.TransitModality;
import com.transit.service.TransitSystemFacade;
import java.io.Console;
import java.util.Scanner;

public class ConsoleUI {
    private final TransitSystemFacade facade;
    private final Scanner scanner;
    
    private String loggedInCardId = null;
    private String loggedInUserName = null;

    public ConsoleUI(TransitSystemFacade facade) {
        this.facade = facade;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            if (loggedInCardId == null) {
                running = showLoginMenu();
            } else {
                running = showDashboard();
            }
        }
        System.out.println("\n[SYSTEM] Shutting down. İyi günler! 👋");
    }

    // --- GENERIC INPUT VALIDATION HELPERS ---
    
    // Loops until the user types something that matches the Regex pattern
    private String promptUntilValid(String prompt, String regex, String errorMessage) {
        while (true) {
            System.out.print(prompt + " > ");
            String input = scanner.nextLine().trim();
            if (input.matches(regex)) {
                return input;
            }
            System.out.println("   [!] " + errorMessage);
        }
    }

    // Secure PIN loop helper
    private String promptSecurePinUntilValid(String prompt) {
        while (true) {
            String pin = readSecurePin(prompt + " > ");
            if (pin.matches("\\d{6}")) {
                return pin;
            }
            System.out.println("   [!] PIN must be exactly 6 digits. Try again.");
        }
    }

    private String readSecurePin(String prompt) {
        Console console = System.console();
        if (console != null) {
            char[] passwordChars = console.readPassword(prompt);
            return new String(passwordChars);
        } else {
            System.out.print(prompt);
            return scanner.nextLine().trim();
        }
    }

    // --- MENUS ---

    private boolean showLoginMenu() {
        printHeader();
        System.out.println("  [1] 🔐 Log In to Your Card");
        System.out.println("  [2] 📝 Register New Smart Card");
        System.out.println("  [0] 🚪 Exit System");
        System.out.println("-------------------------------------------");
        
        String choice = promptUntilValid("Select option", "[012]", "Invalid selection. Enter 0, 1, or 2.");
        switch (choice) {
            case "1": handleLogin(); pause(); return true;
            case "2": handleNewCard(); pause(); return true;
            case "0": return false;
            default: return true;
        }
    }

    private boolean showDashboard() {
        System.out.println("\n\n===========================================");
        System.out.println(" 👤 USER : " + loggedInUserName.toUpperCase());
        System.out.println(" 💳 CARD : " + loggedInCardId);
        System.out.println("===========================================");
        facade.checkCardBalance(loggedInCardId);
        System.out.println("-------------------------------------------");
        System.out.println("  [1] 💰 Top-Up Wallet");
        System.out.println("  [2] 🎫 Buy Student Monthly Pass (740 TL)"); 
        System.out.println("  [3] 🚇 Tap Card (Enter Transit)");
        System.out.println("  [4] 📜 View Transaction History");
        System.out.println("  [9] 🔓 Log Out");
        System.out.println("  [0] 🚪 Exit System");
        System.out.println("-------------------------------------------");

        String choice = promptUntilValid("Select option", "[012349]", "Invalid selection.");
        switch (choice) {
            case "1": handleTopUp(); pause(); return true;
            case "2": 
                System.out.println("\n[SYSTEM] Processing Subscription Purchase...");
                facade.buyMonthlyPass(loggedInCardId); 
                pause(); 
                return true;
            case "3": handleTap(); pause(); return true;
            case "4": facade.printTransactionHistory(loggedInCardId); pause(); return true;
            case "9": 
                System.out.println("\n[SYSTEM] Logging out..."); 
                loggedInCardId = null; 
                loggedInUserName = null; 
                return true;
            case "0": return false;
            default: return true;
        }
    }

    // --- WORKFLOWS ---

    private void handleLogin() {
        System.out.println("\n--- 🔐 SECURE LOGIN ---");
        String id = promptUntilValid("Enter 11-Digit Card Number", "\\d{11}", "Card ID must be exactly 11 digits.");
        String pin = promptSecurePinUntilValid("Enter 6-Digit PIN");

        SmartCard authCard = facade.login(id, pin);
        if (authCard != null) {
            System.out.println("\n[SUCCESS] Authentication verified.");
            this.loggedInCardId = authCard.getCardId();
            this.loggedInUserName = authCard.getProfile().getName();
        } else {
            System.out.println("\n[DENIED] Incorrect Card Number or PIN.");
        }
    }

    private void handleNewCard() {
        System.out.println("\n--- 📝 REGISTRATION TERMINAL ---");
        
        // Notice how clean this is! It won't let the user proceed until the format is perfect.
        String type = promptUntilValid("Profile Type (Standard/Student/Senior)", "(?i)^(standard|student|senior)$", "Must be Standard, Student, or Senior.");
        
        System.out.print("Enter Name > ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Enter Surname > ");
        String surname = scanner.nextLine().trim();
        
        String tc = promptUntilValid("Enter TC Kimlik (11 digits)", "\\d{11}", "TC Kimlik must be exactly 11 digits.");
        String phone = promptUntilValid("Enter Phone Number (e.g. 5551234567)", "^(?:\\+90|0)?(5\\d{9})$", "Invalid Turkish phone format. Must start with 5 and be 10 digits.");
        
        String pin1, pin2;
        while (true) {
            pin1 = promptSecurePinUntilValid("Create 6-Digit PIN");
            pin2 = readSecurePin("Confirm 6-Digit PIN > ");
            if (pin1.equals(pin2)) break;
            System.out.println("   [!] PINs do not match. Let's try creating the PIN again.");
        }

        System.out.println("\n[SYSTEM] Processing registration...");
        facade.requestNewCard(type, name, surname, tc, phone, pin1);
    }

    private void handleTopUp() {
        System.out.println("\n--- 💰 WALLET TERMINAL ---");
        System.out.println(" [A] 50 TL   [B] 100 TL   [C] 200 TL   [D] Custom");
        
        String amountChoice = promptUntilValid("Select Amount", "(?i)^[a-d]$", "Please type A, B, C, or D.");
        
        double amount = 0;
        switch (amountChoice.toUpperCase()) {
            case "A": amount = 50.0; break;
            case "B": amount = 100.0; break;
            case "C": amount = 200.0; break;
            case "D":
                while (true) {
                    System.out.print("Enter custom amount (TL) > ");
                    try { 
                        amount = Double.parseDouble(scanner.nextLine().trim());
                        if (amount > 0) break;
                        System.out.println("   [!] Amount must be greater than zero.");
                    } catch (Exception e) { 
                        System.out.println("   [!] Invalid number format."); 
                    }
                }
                break;
        }
        
        System.out.println("\n[SYSTEM] Processing transaction...");
        facade.topUpWallet(loggedInCardId, amount);
    }

    private void handleTap() {
        System.out.println("\n--- 🚇 TURNSTILE SIMULATOR ---");
        System.out.println(" [1] BUS   [2] FERRY   [3] RAILWAY");
        
        String modChoice = promptUntilValid("Select Transit Mode", "[123]", "Please type 1, 2, or 3.");
        
        TransitModality modality = TransitModality.BUS;
        switch (modChoice) {
            case "1": modality = TransitModality.BUS; break;
            case "2": modality = TransitModality.FERRY; break;
            case "3": modality = TransitModality.RAILWAY; break;
        }
        facade.tapCardForRide(loggedInCardId, modality);
    }

    // --- UI HELPERS ---

    private void printHeader() {
        System.out.println("\n\n");
        System.out.println("===========================================");
        System.out.println("   🏙️  İZMİR PUBLIC TRANSIT TERMINAL 🏙️   ");
        System.out.println("===========================================");
    }

    private void pause() {
        System.out.print("\nPress [ENTER] to continue > ");
        scanner.nextLine();
    }
}