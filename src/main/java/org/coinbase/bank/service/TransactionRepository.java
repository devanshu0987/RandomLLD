package org.coinbase.bank.service;

import org.coinbase.bank.model.Transaction;
import org.coinbase.bank.model.User;

import java.util.List;

public interface TransactionRepository {

    public String addTransaction(Transaction t);
    public List<User> topKUsersWithMaxTransactionCount();
}
