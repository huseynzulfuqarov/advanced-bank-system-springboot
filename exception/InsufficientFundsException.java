package org.example.Course.Module2.BankManagement.exception;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException() {}
    public InsufficientFundsException(String message) {
        super(message);
    }
}
