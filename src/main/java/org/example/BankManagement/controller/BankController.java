package org.example.BankManagement.controller;

import org.example.BankManagement.dto.CreateAccountRequest;
import org.example.BankManagement.exception.AccountNotFoundException;
import org.example.BankManagement.exception.InsufficientFundsException;
import org.example.BankManagement.exception.InvalidAmountException;
import org.example.BankManagement.model.Account;
import org.example.BankManagement.model.Customer;
import org.example.BankManagement.model.Transaction;
import org.example.BankManagement.service.IBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bank")
public class BankController {

    private final IBankService bankService;

    @Autowired
    public BankController(IBankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest request) {
        Account account = bankService.createAccount(
                request.getFullName(),
                request.getAddress(),
                request.getPassword(),
                request.getType(),
                request.getInitialBalance());
        return ResponseEntity.ok(account);
    }

    @GetMapping("/accounts/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) throws AccountNotFoundException {
        return ResponseEntity.ok(bankService.findAccount(accountNumber));
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(bankService.getAllAccounts());
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(bankService.getAllCustomers());
    }

    @GetMapping("/accounts/{accountNumber}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable String accountNumber)
            throws AccountNotFoundException {
        return ResponseEntity.ok(bankService.getTransactionsByAccountNumber(accountNumber));
    }

    @PostMapping("/accounts/{accountNumber}/deposit")
    public ResponseEntity<Void> deposit(@PathVariable String accountNumber, @RequestParam double amount)
            throws AccountNotFoundException, InvalidAmountException {
        bankService.deposit(accountNumber, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accounts/{accountNumber}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable String accountNumber, @RequestParam double amount)
            throws AccountNotFoundException, InvalidAmountException, InsufficientFundsException {
        bankService.withdraw(accountNumber, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestParam String fromAccount, @RequestParam String toAccount,
            @RequestParam double amount)
            throws AccountNotFoundException, InvalidAmountException, InsufficientFundsException {
        bankService.transferFunds(fromAccount, toAccount, amount);
        return ResponseEntity.ok().build();
    }
}
