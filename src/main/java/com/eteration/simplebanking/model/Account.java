package com.eteration.simplebanking.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String owner;
    private String accountNumber;
    private double balance;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
    private List<Transaction> transactions = new ArrayList<>();

    public Account() {
    }

    public Account(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = 0; // Initial balance set to 0
    }

    // Process a generic transaction
    public void post(Transaction transaction) throws InsufficientBalanceException {
        transaction.process(this);
        transactions.add(transaction);
    }

    // Deposit method utilizing DepositTransaction
    public void deposit(double amount) throws InsufficientBalanceException {
        DepositTransaction deposit = new DepositTransaction(amount);
        this.post(deposit);
    }

    // Withdrawal method utilizing WithdrawalTransaction
    public void withdraw(double amount) throws InsufficientBalanceException {
        WithdrawalTransaction withdrawal = new WithdrawalTransaction(amount);
        this.post(withdrawal);
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getOwner() {
        return owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}