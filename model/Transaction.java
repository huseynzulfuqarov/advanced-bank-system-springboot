package org.example.Course.Module2.BankManagement.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Transaction {
    private final String transactionId;
    private LocalDate transactionDate;
    private TransactionType  transactionType;
    private double amount;

    public Transaction(LocalDate transactionDate, TransactionType transactionType, double amount) {
        this.transactionId = UUID.randomUUID().toString();
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Transaction that)) return false;
        return Double.compare(amount, that.amount) == 0 && Objects.equals(transactionId, that.transactionId) && Objects.equals(transactionDate, that.transactionDate) && transactionType == that.transactionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, transactionDate, transactionType, amount);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", transactionDate=" + transactionDate +
                ", transactionType=" + transactionType +
                ", amount=" + amount +
                '}';
    }
}
