package org.coda.kvstore;

import java.util.*;

public class KvStoreWithUndo {
    private Map<String, String> store;
    Deque<PutOperation> undo, redo;

    public KvStoreWithUndo() {
        store = new HashMap<>();
        undo = new ArrayDeque<>();
        redo = new ArrayDeque<>();
    }

    public void put(String key, String value) {
        if (store.containsKey(key)) {
            String prevState = store.get(key);
            String newState = value;
            undo.push(new PutOperation(key, value, prevState));
        } else {
            // which means, it is a new. When we undo this, we need to erase the
            // element from store as well.
            undo.push(new PutOperation(key, value, null));
        }
        store.put(key, value);
    }

    public String get(String key) {
        return store.get(key);
    }

    public void undo() {
        if (!undo.isEmpty()) {
            PutOperation operationToUndo = undo.peek();
            undo.pop();
            redo.push(operationToUndo.getUndoOperation());

            // apply state change to main state.
            if (operationToUndo.prevValue == null) {
                // this key was a new key, so when we undo, it should be erased.
                store.remove(operationToUndo.key);
            } else {
                // just apply it on the store.
                store.put(operationToUndo.key, operationToUndo.prevValue);
            }
        }
    }

    public void redo() {
        if (!redo.isEmpty()) {
            // attach the transaction to the main state.
            PutOperation operationToRedo = redo.peek();
            redo.pop();
            undo.push(operationToRedo.getUndoOperation());

            // apply state change to main state.
            store.put(operationToRedo.key, operationToRedo.prevValue);
        }
    }
}