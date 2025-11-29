package org.example.BankManagement.service;

import org.example.BankManagement.exception.AccountNotFoundException;
import org.example.BankManagement.exception.InsufficientFundsException;
import org.example.BankManagement.exception.InvalidAmountException;
import org.example.BankManagement.model.*;
import org.example.BankManagement.repository.AccountRepository;
import org.example.BankManagement.repository.CustomerRepository;
import org.example.BankManagement.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankService implements IBankService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public BankService(AccountRepository accountRepository, CustomerRepository customerRepository,
            TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public Account createAccount(String customerFullName, String customerAddress, String accountPassword,
            String accountType, double initialBalance) {
        Customer newCustomer = new Customer(customerFullName, customerAddress, accountPassword);
        customerRepository.save(newCustomer);

        Account newAccount;
        if ("SAVINGS".equalsIgnoreCase(accountType)) {
            newAccount = new SavingsAccount(initialBalance, newCustomer, 2.0);
        } else if ("CHECKING".equalsIgnoreCase(accountType)) {
            newAccount = new CheckingAccount(initialBalance, newCustomer, 500.0);
        } else {
            return null;
        }
        return accountRepository.save(newAccount);
    }

    @Override
    public Account findAccount(String accountNumber) throws AccountNotFoundException {
        return accountRepository.findById(accountNumber)
                .orElseThrow(
                        () -> new AccountNotFoundException("Account with number '" + accountNumber + "' not found."));
    }

    @Override
    public Customer findCustomerByPasswordAndId(String customerId, String password) {
        return customerRepository.findById(customerId)
                .filter(c -> c.getAccountPassword().equals(password))
                .orElse(null);
    }

    @Override
    public List<Account> findAccountsByCustomerId(String customerId) {
        return accountRepository.findAll().stream()
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
        return customerRepository.findAll();
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional
    public void deposit(String accountNumber, double amount) throws AccountNotFoundException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive.");
        }
        Account account = findAccount(accountNumber);
        account.deposit(amount);
        Transaction transaction = new Transaction(LocalDate.now(), TransactionType.DEPOSIT, amount);
        account.addTransaction(transaction);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void withdraw(String accountNumber, double amount)
            throws AccountNotFoundException, InvalidAmountException, InsufficientFundsException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive.");
        }
        Account account = findAccount(accountNumber);
        account.withdraw(amount);
        Transaction transaction = new Transaction(LocalDate.now(), TransactionType.WITHDRAWAL, amount);
        account.addTransaction(transaction);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void transferFunds(String fromAccountNumber, String toAccountNumber, double amount)
            throws AccountNotFoundException, InvalidAmountException, InsufficientFundsException {
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

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
}