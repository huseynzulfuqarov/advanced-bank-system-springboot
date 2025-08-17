package org.example.Course.Module2.BankManagement.model;

import org.example.Course.Module2.BankManagement.exception.InsufficientFundsException;

import java.util.Objects;

public class CheckingAccount extends Account{
    private final double overdraftLimit;

    public CheckingAccount(double balance, Customer customer, double overdraftLimit) {
        super(balance, customer);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {

        if(overdraftLimit + this.getBalance() <= amount){
            throw new InsufficientFundsException("Overdraft limit exceeded.");
        }
        else{
            this.setBalance(this.getBalance() - amount);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CheckingAccount that)) return false;
        if (!super.equals(o)) return false;
        return Double.compare(overdraftLimit, that.overdraftLimit) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), overdraftLimit);
    }

    @Override
    public String toString() {
        return "CheckingAccount{" +
                "overdraftLimit=" + overdraftLimit + super.toString() +
                '}';
    }
}
