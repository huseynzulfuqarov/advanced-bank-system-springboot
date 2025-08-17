package org.example.Course.Module2.BankManagement.model;

import java.util.UUID;

public class Customer {
    private final String customerId;
    private String fullName;
    private String address;
    private final String accountPassword;

    public Customer(String fullName, String address, String accountPassword) {
        this.customerId = UUID.randomUUID().toString();
        this.fullName = fullName;
        this.address = address;
        this.accountPassword = accountPassword;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccountPassword() {
        return accountPassword;
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