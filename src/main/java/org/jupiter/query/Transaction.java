package org.jupiter.query;

import java.time.LocalDateTime;

public class Transaction {
    public String id;
    public LocalDateTime timestamp;
    public Integer amount;
    public String Currency;
    public String userId;

    public Transaction(String id, LocalDateTime timestamp, Integer amount) {
        this.id = id;
        this.timestamp = timestamp;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", amount=" + amount +
                '}';
    }
}
