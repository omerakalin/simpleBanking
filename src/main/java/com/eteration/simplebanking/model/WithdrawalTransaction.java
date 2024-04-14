package com.eteration.simplebanking.model;

import javax.persistence.Entity;

@Entity
public class WithdrawalTransaction extends Transaction {

    public WithdrawalTransaction() {}

    public WithdrawalTransaction(double amount) {
        super(amount);
    }

    @Override
    public void process(Account account) throws InsufficientBalanceException {
        double newBalance = account.getBalance() - this.amount;
        if (newBalance < 0) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal");
        }
        account.setBalance(newBalance);
    }
}
