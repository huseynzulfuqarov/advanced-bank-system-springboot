package org.example.Course.Module2.BankManagement.model;

import org.example.Course.Module2.BankManagement.exception.InsufficientFundsException;

import java.util.*;

public abstract class Account {
    private final String accountNumber;
    private double balance;
    private Customer owner;
    private List<Transaction> transactions = new ArrayList<>();

    public Account(double balance, Customer customer) {
        this.accountNumber = UUID.randomUUID().toString();
        this.balance = balance;
        this.owner = customer;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public List<Transaction> getTransactions() {
        return List.copyOf(transactions);
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions =  new ArrayList<>(transactions);
    }

    public void addTransaction(Transaction transactions) {
        this.transactions.add(transactions);
    }

    public void deposit(double amount){
        this.balance += amount;
    }

    public abstract void withdraw(double amount) throws InsufficientFundsException;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Account account)) return false;
        return Double.compare(balance, account.balance) == 0 && Objects.equals(accountNumber, account.accountNumber) && Objects.equals(owner, account.owner) && Objects.equals(transactions, account.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, balance, owner, transactions);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", owner=" + owner +
                ", transactions=" + transactions +
                '}';
    }
}
