package org.example.Course.Module2.BankManagement.exception;

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException() {}
    public AccountNotFoundException(String message) {
        super(message);
    }
}
