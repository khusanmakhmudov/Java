package com.transit.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.transit.domain.SmartCard;
import com.transit.domain.TransitModality;
import com.transit.infrastructure.InMemoryCardRepository;
import com.transit.infrastructure.InMemoryTransactionRepository;
import com.transit.service.CardManagementService;
import com.transit.service.TransactionService;
import com.transit.service.WalletService;

public class TransitSystemTestSuite {

    private InMemoryCardRepository cardRepo;
    private InMemoryTransactionRepository txRepo;
    private CardManagementService cardService;
    private WalletService walletService;
    private TransactionService transactionService;

    // 1. ARCHITECTURAL SETUP: Runs before EVERY test to ensure a clean state
    @Before
    public void setUp() {
        cardRepo = new InMemoryCardRepository();
        txRepo = new InMemoryTransactionRepository();
        cardService = new CardManagementService(cardRepo);
        walletService = new WalletService(cardRepo, txRepo);
        transactionService = new TransactionService(cardRepo, txRepo);
    }

    // --- TEST BLOCK 1: DOMAIN CONSTRAINTS ---

    @Test
    public void testMaxBalanceConstraint() {
        // Arrange
        SmartCard card = cardService.issueNewCard("STANDARD", "John", "Doe", "11111111111", "5551234567", "123456");
        
        // Act: Try to top up beyond the 5000 TL limit
        boolean success1 = walletService.topUp(card.getCardId(), 4000.0); // Should pass
        boolean success2 = walletService.topUp(card.getCardId(), 2000.0); // Should fail (4000 + 2000 > 5000)

        // Assert
        assertTrue("First top-up should succeed", success1);
        assertFalse("Second top-up should be blocked by MAX_BALANCE domain rule", success2);
        assertEquals("Balance should remain at 4000", 4000.0, card.getBalance(), 0.01);
    }

    // --- TEST BLOCK 2: BUSINESS RULES (SUBSCRIPTIONS) ---

    @Test
    public void testStudentCanBuyMonthlyPass() {
        // Arrange: Create a student and give them enough money
        SmartCard studentCard = cardService.issueNewCard("STUDENT", "Ali", "Veli", "12345678901", "5559876543", "123456");
        walletService.topUp(studentCard.getCardId(), 1000.0);

        // Act: Buy the pass
        boolean passBought = walletService.buyMonthlyPass(studentCard.getCardId());

        // Assert
        assertTrue("Student should be allowed to buy the pass", passBought);
        assertEquals("740 TL should be deducted", 260.0, studentCard.getBalance(), 0.01);
        assertTrue("Card should now have an active subscription", studentCard.hasActiveSubscription());
        assertEquals("Should have exactly 100 credits", 100.0, studentCard.getSubscription().getRemainingCredits(), 0.01);
    }

    @Test
    public void testStandardUserCannotBuyMonthlyPass() {
        // Arrange: Create a standard user and give them enough money
        SmartCard standardCard = cardService.issueNewCard("STANDARD", "Ayşe", "Yılmaz", "11111111111", "5551112233", "123456");
        walletService.topUp(standardCard.getCardId(), 1000.0);

        // Act: Attempt to buy the pass
        boolean passBought = walletService.buyMonthlyPass(standardCard.getCardId());

        // Assert
        assertFalse("Standard user should be BLOCKED from buying a student pass", passBought);
        assertEquals("Money should NOT be deducted", 1000.0, standardCard.getBalance(), 0.01);
        assertFalse("Card should NOT have an active subscription", standardCard.hasActiveSubscription());
    }

    // --- TEST BLOCK 3: STRATEGY PATTERN & TRANSIT LOGIC ---

    @Test
    public void testPassCreditsUsedBeforeWalletBalance() {
        // Arrange: Student with a pass and some extra wallet money
        SmartCard studentCard = cardService.issueNewCard("STUDENT", "Can", "Kaya", "12345678901", "5554445566", "123456");
        walletService.topUp(studentCard.getCardId(), 1000.0); // Load 1000
        walletService.buyMonthlyPass(studentCard.getCardId()); // Spends 740. Balance = 260, Credits = 100

        // Act: Tap into a Ferry (Costs 3 credits)
        boolean tapSuccess = transactionService.processTapIn(studentCard.getCardId(), TransitModality.FERRY);

        // Assert
        assertTrue("Tap should succeed", tapSuccess);
        assertEquals("Ferry should deduct 3 credits", 97.0, studentCard.getSubscription().getRemainingCredits(), 0.01);
        assertEquals("Wallet balance should NOT be touched", 260.0, studentCard.getBalance(), 0.01);
    }

    @Test
    public void testInsufficientFundsBlocksTransit() {
        // Arrange: User with 0 balance
        SmartCard card = cardService.issueNewCard("STANDARD", "Veli", "Demir", "11111111111", "5559998877", "123456");

        // Act: Try to ride the bus
        boolean tapSuccess = transactionService.processTapIn(card.getCardId(), TransitModality.BUS);

        // Assert
        assertFalse("Transit turnstile should BLOCK access", tapSuccess);
    }
}