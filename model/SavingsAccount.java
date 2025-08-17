package org.example.Course.Module2.BankManagement.model;

import org.example.Course.Module2.BankManagement.exception.InsufficientFundsException;

import java.util.Objects;

public class SavingsAccount extends Account {
    private final double interestRate;

    public SavingsAccount(double balance, Customer customer,  double interestRate) {
        super(balance, customer);
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (this.getBalance() - amount < 50) {
            throw new InsufficientFundsException("The minimum balance must be 50 AZN.");
        } else {
            this.setBalance(this.getBalance() - amount);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SavingsAccount that)) return false;
        if (!super.equals(o)) return false;
        return Double.compare(interestRate, that.interestRate) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), interestRate);
    }

    @Override
    public String toString() {
        return "SavingsAccount{" +
                "interestRate=" + interestRate + super.toString() +
                '}';
    }
}
