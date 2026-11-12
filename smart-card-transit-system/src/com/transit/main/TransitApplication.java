package com.transit.main;

import com.transit.infrastructure.InMemoryCardRepository;
import com.transit.infrastructure.InMemoryTransactionRepository;
import com.transit.service.CardManagementService;
import com.transit.service.TransactionService;
import com.transit.service.TransitSystemFacade;
import com.transit.service.WalletService;
import com.transit.presentation.ConsoleUI;

public class TransitApplication {
    public static void main(String[] args) {
        
        // 1. Initialize Infrastructure (High-Performance In-Memory DBs)
        InMemoryCardRepository cardRepo = new InMemoryCardRepository();
        InMemoryTransactionRepository txRepo = new InMemoryTransactionRepository();

        // 2. Initialize Services (Injecting Repositories)
        CardManagementService cardService = new CardManagementService(cardRepo);
        WalletService walletService = new WalletService(cardRepo, txRepo);
        TransactionService transactionService = new TransactionService(cardRepo, txRepo);

        // 3. Initialize Facade (Injecting Services)
        TransitSystemFacade facade = new TransitSystemFacade(cardService, walletService, transactionService);

        // 4. Initialize UI and Start Application
        ConsoleUI ui = new ConsoleUI(facade);
        ui.start();
    }
}