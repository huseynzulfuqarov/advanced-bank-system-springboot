package org.example.Course.Module2.BankManagement.exception;

public class InvalidAmountException extends Exception {
    public InvalidAmountException() {}
    public InvalidAmountException(String message) {
        super(message);
    }
}
