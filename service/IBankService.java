package org.example.Course.Module2.BankManagement.service;

import org.example.Course.Module2.BankManagement.exception.*;
import org.example.Course.Module2.BankManagement.model.Account;
import org.example.Course.Module2.BankManagement.model.Customer;
import org.example.Course.Module2.BankManagement.model.Transaction;

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