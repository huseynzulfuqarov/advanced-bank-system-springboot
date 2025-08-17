package org.example.Course.Module2.BankManagement.ui;

import org.example.Course.Module2.BankManagement.exception.*;
import org.example.Course.Module2.BankManagement.model.Account;
import org.example.Course.Module2.BankManagement.model.Customer;
import org.example.Course.Module2.BankManagement.model.Transaction;
import org.example.Course.Module2.BankManagement.service.IBankService;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final IBankService bankService;
    private final Scanner scanner;

    public ConsoleUI(IBankService bankService) {
        this.bankService = bankService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\nWelcome to the Bank Management System");
            System.out.println("1. Login as Customer");
            System.out.println("2. Login as Bank Worker (Admin)");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    customerLogin();
                    break;
                case "2":
                    adminLogin();
                    break;
                case "3":
                    System.out.println("Thank you for using our bank!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void customerLogin() {
        System.out.print("Enter your customer ID: ");
        String customerId = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        Customer customer = bankService.findCustomerByPasswordAndId(customerId, password);
        if (customer == null) {
            System.out.println("Login failed! Invalid Customer ID or Password.");
        } else {
            startCustomerSession(customer);
        }
    }

    private void adminLogin() {
        System.out.print("Enter your admin ID: ");
        String adminId = scanner.nextLine();
        System.out.print("Enter your admin password: ");
        String password = scanner.nextLine();

        if ("admin".equals(adminId) && "admin".equals(password)) {
            startAdminSession();
        } else {
            System.out.println("Login failed! Invalid admin credentials.");
        }
    }

    private void startAdminSession() {
        while (true) {
            System.out.println("\n--- Admin Panel ---");
            System.out.println("1. List All Customers");
            System.out.println("2. List All Accounts");
            System.out.println("3. Find Accounts by Customer ID");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    List<Customer> customers = bankService.getAllCustomers();
                    System.out.println("\n--- All Customers ---");
                    customers.forEach(System.out::println);
                    break;
                case "2":
                    List<Account> accounts = bankService.getAllAccounts();
                    System.out.println("\n--- All Accounts ---");
                    accounts.forEach(System.out::println);
                    break;
                case "3":
                    System.out.print("Enter customer ID: ");
                    String customerId = scanner.nextLine();
                    List<Account> customerAccounts = bankService.findAccountsByCustomerId(customerId);
                    System.out.println("\n--- Accounts for customer " + customerId + " ---");
                    customerAccounts.forEach(System.out::println);
                    break;
                case "4":
                    System.out.println("Logging out from admin panel...");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void startCustomerSession(Customer loggedInCustomer) {
        System.out.println("\nWelcome, " + loggedInCustomer.getFullName() + "!");
        while(true){
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. Perform operations on an account");
            System.out.println("2. View all my accounts");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            if ("3".equals(choice)) {
                System.out.println("Logging out...");
                break;
            }
            if("2".equals(choice)){
                List<Account> myAccounts = bankService.findAccountsByCustomerId(loggedInCustomer.getCustomerId());
                myAccounts.forEach(System.out::println);
                continue;
            }

            System.out.print("Enter the account number you want to work with: ");
            String accountNumber = scanner.nextLine();

            try {
                Account selectedAccount = bankService.findAccount(accountNumber);
                if (!selectedAccount.getOwner().getCustomerId().equals(loggedInCustomer.getCustomerId())) {
                    System.out.println("Error: You do not have permission to access this account.");
                    continue;
                }
                accountOperationsMenu(selectedAccount);
            } catch (AccountNotFoundException e) {
                System.err.println("ERROR: " + e.getMessage());
            }
        }
    }

    private void accountOperationsMenu(Account account) {
        while(true){
            System.out.println("\n--- Operations for Account: " + account.getAccountNumber() + " ---");
            System.out.println("Current Balance: " + account.getBalance());
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer Funds");
            System.out.println("4. View Transaction History");
            System.out.println("5. Back to account selection");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            try {
                switch(choice) {
                    case "1":
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = Double.parseDouble(scanner.nextLine());
                        bankService.deposit(account.getAccountNumber(), depositAmount);
                        System.out.println("Deposit successful.");
                        break;
                    case "2":
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = Double.parseDouble(scanner.nextLine());
                        bankService.withdraw(account.getAccountNumber(), withdrawAmount);
                        System.out.println("Withdrawal successful.");
                        break;
                    case "3":
                        System.out.print("Enter destination account number: ");
                        String toAcc = scanner.nextLine();
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = Double.parseDouble(scanner.nextLine());
                        bankService.transferFunds(account.getAccountNumber(), toAcc, transferAmount);
                        System.out.println("Transfer successful.");
                        break;
                    case "4":
                        List<Transaction> transactions = bankService.getTransactionsByAccountNumber(account.getAccountNumber());
                        System.out.println("\n--- Transaction History ---");
                        transactions.forEach(System.out::println);
                        break;
                    case "5":
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
                account = bankService.findAccount(account.getAccountNumber());
            } catch (AccountNotFoundException | InsufficientFundsException | InvalidAmountException e) {
                System.err.println("OPERATION FAILED: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("Invalid amount. Please enter a number.");
            }
        }
    }
}