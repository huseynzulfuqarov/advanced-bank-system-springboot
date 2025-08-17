package org.example.BankManagement.service;

import org.example.BankManagement.exception.AccountNotFoundException;
import org.example.BankManagement.exception.InsufficientFundsException;
import org.example.BankManagement.exception.InvalidAmountException;
import org.example.BankManagement.model.Account;
import org.example.BankManagement.model.Customer;
import org.example.BankManagement.model.Transaction;

import java.util.List;

public interface IBankService {
    Account createAccount(String customerFullName, String customerAddress, String accountPassword, String accountType, double initialBalance);

    Account findAccount(String accountNumber) throws AccountNotFoundException;

    Customer findCustomerByPasswordAndId(String customerId, String password);

    List<Account> findAccountsByCustomerId(String customerId);

    List<Transaction> getTransactionsByAccountNumber(String accountNumber) throws AccountNotFoundException;

    List<Customer> getAllCustomers();

    List<Account> getAllAccounts();

    void deposit(String accountNumber, double amount) throws AccountNotFoundException, InvalidAmountException;

    void withdraw(String accountNumber, double amount) throws AccountNotFoundException, InvalidAmountException, InsufficientFundsException;

    void transferFunds(String fromAccountNumber, String toAccountNumber, double amount) throws AccountNotFoundException, InvalidAmountException, InsufficientFundsException;
}