package org.example.BankManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        // Previous Console Logic
        /*
         * IBankService bankService = new BankService();
         * 
         * Account acc1 = bankService.createAccount("Nicat Pasazade",
         * "Baku, Azerbaijan", "pass123", "SAVINGS", 1000);
         * Account acc2 = bankService.createAccount("Ali Valiyev",
         * "Sumgait, Azerbaijan", "pass456", "CHECKING", 500);
         * 
         * System.out.println("--- Sample Customer Credentials ---");
         * System.out.println("Customer 1 ID: " + acc1.getOwner().getCustomerId() +
         * " | Password: " + acc1.getOwner().getAccountPassword());
         * System.out.println("Customer 2 ID: " + acc2.getOwner().getCustomerId() +
         * " | Password: " + acc2.getOwner().getAccountPassword());
         * System.out.println("------------------------------------");
         * System.out.println("Admin ID: admin | Password: admin");
         * 
         * ConsoleUI consoleUI = new ConsoleUI(bankService);
         * consoleUI.start();
         */
    }
}