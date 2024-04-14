package com.eteration.simplebanking.services;

import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eteration.simplebanking.repository.AccountRepository;
import javax.transaction.Transactional;
import java.util.UUID;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public TransactionStatus credit(String accountNumber, double amount) throws Exception {
        Optional<Account> accountOpt = findAccount(accountNumber); // Change to use findAccount
        if (!accountOpt.isPresent()) {
            throw new Exception("Account not found");
        }
        Account account = accountOpt.get();
        DepositTransaction transaction = new DepositTransaction(amount);
        account.post(transaction);
        accountRepository.save(account);
        return new TransactionStatus("OK", UUID.randomUUID().toString());
    }

    @Transactional
    public TransactionStatus debit(String accountNumber, double amount) throws Exception {
        Optional<Account> accountOpt = findAccount(accountNumber); // Change to use findAccount
        if (!accountOpt.isPresent()) {
            throw new Exception("Account not found");
        }
        Account account = accountOpt.get();
        WithdrawalTransaction transaction = new WithdrawalTransaction(amount);
        try {
            account.post(transaction);
        } catch (InsufficientBalanceException e) {
            throw e;
        }
        accountRepository.save(account);
        return new TransactionStatus("OK", UUID.randomUUID().toString());
    }

    public Account getAccount(String accountNumber) throws Exception {
        Optional<Account> accountOpt = findAccount(accountNumber);
        if (!accountOpt.isPresent()) {
            throw new Exception("Account not found");
        }
        return accountOpt.get();
    }

    public Optional<Account> findAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }
}