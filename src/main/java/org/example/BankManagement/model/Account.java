package org.example.BankManagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.BankManagement.exception.InsufficientFundsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public abstract class Account {
    @Id
    private String accountNumber;
    private double balance;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer owner;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Transaction> transactions = new ArrayList<>();

    public Account(double balance, Customer customer) {
        this.accountNumber = UUID.randomUUID().toString();
        this.balance = balance;
        this.owner = customer;
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public abstract void withdraw(double amount) throws InsufficientFundsException;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Account account))
            return false;
        return Double.compare(balance, account.balance) == 0 && Objects.equals(accountNumber, account.accountNumber)
                && Objects.equals(owner, account.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, balance, owner);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", owner=" + owner +
                '}';
    }
}
