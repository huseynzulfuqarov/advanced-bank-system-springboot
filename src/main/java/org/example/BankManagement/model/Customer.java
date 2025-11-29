package org.example.BankManagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {
    @Id
    private String customerId;
    private String fullName;
    private String address;
    @com.fasterxml.jackson.annotation.JsonIgnore
    private String accountPassword;

    public Customer(String fullName, String address, String accountPassword) {
        this.customerId = UUID.randomUUID().toString();
        this.fullName = fullName;
        this.address = address;
        this.accountPassword = accountPassword;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}