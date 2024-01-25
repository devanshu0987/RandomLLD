package org.coda.kvstore;

public class PutOperation {
    public String key;
    public String newValue;
    public String prevValue;
    public PutOperation(String k, String nv, String pv) {
        this.key = k;
        this.newValue = nv;
        this.prevValue = pv;
    }

    public PutOperation getUndoOperation() {
        return new PutOperation(key, prevValue, newValue);
    }

    @Override
    public String toString() {
        return this.key + " + PREV VALUE : " + this.prevValue + " + NEW VALUE : " + this.newValue;
    }
}