package org.example.Course.Module2.BankManagement.service;

import org.example.Course.Module2.BankManagement.exception.*;
import org.example.Course.Module2.BankManagement.model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BankService implements IBankService {
    private final List<Account> accounts = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();

    @Override
    public Account createAccount(String customerFullName, String customerAddress, String accountPassword, String accountType, double initialBalance) {
        Customer newCustomer = new Customer(customerFullName, customerAddress, accountPassword);
        customers.add(newCustomer);

        Account newAccount;
        if ("SAVINGS".equalsIgnoreCase(accountType)) {
            newAccount = new SavingsAccount(initialBalance, newCustomer, 2.0);
        } else if ("CHECKING".equalsIgnoreCase(accountType)) {
            newAccount = new CheckingAccount(initialBalance, newCustomer, 500.0);
        } else {
            return null;
        }
        accounts.add(newAccount);
        return newAccount;
    }

    @Override
    public Account findAccount(String accountNumber) throws AccountNotFoundException {
        return accounts.stream()
                .filter(acc -> acc.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("Account with number '" + accountNumber + "' not found."));
    }

    @Override
    public Customer findCustomerByPasswordAndId(String customerId, String password) {
        return customers.stream()
                .filter(c -> c.getCustomerId().equals(customerId) && c.getAccountPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Account> findAccountsByCustomerId(String customerId) {
        return accounts.stream()
                .filter(acc -> acc.getOwner().getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> getTransactionsByAccountNumber(String accountNumber) throws AccountNotFoundException {
        Account account = findAccount(accountNumber);
        return account.getTransactions();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    @Override
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts);
    }

    @Override
    public void deposit(String accountNumber, double amount) throws AccountNotFoundException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive.");
        }
        Account account = findAccount(accountNumber);
        account.deposit(amount);
        Transaction transaction = new Transaction(LocalDate.now(), TransactionType.DEPOSIT, amount);
        account.addTransaction(transaction);
    }

    @Override
    public void withdraw(String accountNumber, double amount) throws AccountNotFoundException, InvalidAmountException, InsufficientFundsException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive.");
        }
        Account account = findAccount(accountNumber);
        account.withdraw(amount);
        Transaction transaction = new Transaction(LocalDate.now(), TransactionType.WITHDRAWAL, amount);
        account.addTransaction(transaction);
    }

    @Override
    public void transferFunds(String fromAccountNumber, String toAccountNumber, double amount) throws AccountNotFoundException, InvalidAmountException, InsufficientFundsException {
        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new InvalidAmountException("Cannot transfer funds to the same account.");
        }
        Account fromAccount = findAccount(fromAccountNumber);
        Account toAccount = findAccount(toAccountNumber);

        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

        Transaction transferOut = new Transaction(LocalDate.now(), TransactionType.TRANSFER, -amount);
        fromAccount.addTransaction(transferOut);

        Transaction transferIn = new Transaction(LocalDate.now(), TransactionType.TRANSFER, amount);
        toAccount.addTransaction(transferIn);
    }
}