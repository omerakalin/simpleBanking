package com.eteration.simplebanking.model;

import javax.persistence.Entity;

@Entity
public class DepositTransaction extends Transaction {

    public DepositTransaction() {
    }

    public DepositTransaction(double amount) {
        super(amount);
    }

    @Override
    public void process(Account account) {
        double newBalance = account.getBalance() + this.amount;
        account.setBalance(newBalance);
    }
}
