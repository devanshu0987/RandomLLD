package org.coinbase.bank.model;

public class BankAccount {
    private String id;
    private User user;
    private double balance;

    public BankAccount(String id, User user) {
        this.id = id;
        this.user = user;
        balance = 0;
    }

    boolean deposit(double amount) {
        balance += amount;
        return true;
    }

    boolean withdraw(double amount) {
        if (balance < amount)
            return false;
        balance -= amount;
        return true;
    }

    public double getBalance() {
        return balance;
    }
}
