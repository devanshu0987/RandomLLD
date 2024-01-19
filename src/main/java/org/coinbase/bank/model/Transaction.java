package org.coinbase.bank.model;

public class Transaction {
    private String id;
    private User initiator;
    private TransactionType type;

    public User getInitiator() {
        return initiator;
    }
}
