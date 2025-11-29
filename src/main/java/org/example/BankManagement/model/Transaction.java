package org.example.BankManagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    @Id
    private String transactionId;
    private LocalDate transactionDate;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private double amount;

    public Transaction(LocalDate transactionDate, TransactionType transactionType, double amount) {
        this.transactionId = UUID.randomUUID().toString();
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Transaction that))
            return false;
        return Double.compare(amount, that.amount) == 0 && Objects.equals(transactionId, that.transactionId)
                && Objects.equals(transactionDate, that.transactionDate) && transactionType == that.transactionType;
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
