package org.example.BankManagement.dto;

import lombok.Data;

@Data
public class CreateAccountRequest {
    private String fullName;
    private String address;
    private String password;
    private String type;
    private double initialBalance;
}
