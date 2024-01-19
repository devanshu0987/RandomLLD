package org.coinbase.bank.service.impl;

import org.coinbase.bank.model.Transaction;
import org.coinbase.bank.model.User;
import org.coinbase.bank.service.TransactionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTransactionRepository implements TransactionRepository {
    Map<User, List<Transaction>> transactions;
    Map<User, Integer> transactionFrequency;

    public InMemoryTransactionRepository() {
        transactions = new HashMap<>();
        transactionFrequency = new HashMap<>();
    }

    @Override
    public String addTransaction(Transaction trx) {
        User initiator = trx.getInitiator();
        transactions.compute(initiator, (key, value) -> {
            if (value == null) {
                transactionFrequency.put(initiator, 1);
                return List.of(trx);
            }
            value.add(trx);
            transactionFrequency.computeIfPresent(initiator, (k, v) -> v + 1);
            return value;
        });
        return "";
    }

    @Override
    public List<User> topKUsersWithMaxTransactionCount() {
        List<User> users = new ArrayList<>();
        return users;
    }
}
