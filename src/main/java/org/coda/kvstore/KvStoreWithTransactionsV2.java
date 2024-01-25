package org.coda.kvstore;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/*
Functional Requirements
1. KV Store should support get, put, delete.
2. get should return the latest state. Uncommited state works.
3. Hierarchical transactions are allowed. Latest operations are made on latest transactions.
4. Rollback: If you begin a transaction , it can be rolled back. If you commit it, it is gone forever.
5. Commit applies the transaction and it is poped out.
 */
public class KvStoreWithTransactionsV2 {
    Deque<Map<Integer, Integer>> transactionStack;

    public KvStoreWithTransactionsV2() {
        transactionStack = new ArrayDeque<>();
        transactionStack.push(new HashMap<>());
    }

    public Integer get(Integer key) {
        for (var item : transactionStack) {
            if (item.containsKey(key)) {
                return item.get(key);
            }
        }
        return null;
    }

    public void set(Integer key, Integer value) {
        transactionStack.peek().put(key, value);
    }

    public void delete(Integer key) {
        transactionStack.peek().put(key, null);
    }

    public void begin() {
        transactionStack.push(new HashMap<>());
    }

    public void commit() {
        if(transactionStack.size() <= 1) {
            return;
        }
        // apply the latest transaction to the base transaction.
        var operationsToCommit = transactionStack.peek();
        for (var item : operationsToCommit.entrySet()) {
            transactionStack.getLast().put(item.getKey(), item.getValue());
        }
        transactionStack.pop();
    }

    public void rollback() {
        // we do not want to accidentally pop the base transaction.
        if(transactionStack.size() > 1) {
            transactionStack.pop();
        }
    }
}
