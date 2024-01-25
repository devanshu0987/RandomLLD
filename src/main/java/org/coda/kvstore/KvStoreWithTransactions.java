package org.coda.kvstore;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/*
Functional Requirements
1. KV Store should support get, put, delete.
2. get should return the latest committed state. No dirty reads.
3. Hierarchical transactions are allowed. Latest operations are made on latest transactions.
 */
public class KvStoreWithTransactions {
    Map<Integer, Integer> store;
    Deque<Map<Integer, Integer>> transactionStack;

    public KvStoreWithTransactions() {
        store = new HashMap<>();
        transactionStack = new ArrayDeque<>();
    }

    // Returns the latest committed state.
    public Integer get(Integer key) {
        return store.get(key);
    }

    public Integer lastestGet(Integer key) {
        for (var item : transactionStack) {
            if (item.containsKey(key)) {
                return item.get(key);
            }
        }
        return store.get(key);
    }

    // Puts can only be done by starting a transaction.

    public void set(Integer key, Integer value) {
        if (transactionStack.isEmpty()) {
            store.put(key, value);
        } else {
            transactionStack.peek().put(key, value);
        }
    }

    public void delete(Integer key) {
        store.put(key, null);
    }

    public void begin() {
        transactionStack.push(new HashMap<>());
    }

    public void commit() {
        // apply each key to the main data store.
        var transactions = transactionStack.peek();
        for (var item : transactions.entrySet()) {
            store.put(item.getKey(), item.getValue());
        }
        transactionStack.pop();
    }
}
