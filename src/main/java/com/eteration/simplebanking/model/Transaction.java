package com.eteration.simplebanking.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    protected double amount;
    protected Date transactionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    protected Account account;

    public Transaction() {
    }

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = new Date();
    }

    public abstract void process(Account account) throws InsufficientBalanceException;

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return transactionDate;
    }
}
