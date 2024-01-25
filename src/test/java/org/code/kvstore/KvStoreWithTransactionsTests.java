package org.code.kvstore;

import org.coda.kvstore.KvStoreWithTransactions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KvStoreWithTransactionsTests {
    @Test
    public void testKvStore() {
        KvStoreWithTransactions kv = new KvStoreWithTransactions();
        kv.set(1, 3);

        assertEquals(kv.get(1), 3);
        assertEquals(kv.get(2), null);
    }

    @Test
    public void testKvStoreSingleTransaction() {
        KvStoreWithTransactions kv = new KvStoreWithTransactions();
        kv.set(1, 3);

        kv.begin();
        kv.set(2, 4);
        assert kv.get(1) == 3;
        assert kv.lastestGet(2) == 4;
        kv.commit();

        assert kv.get(1) == 3;
        assert kv.get(2) == 4;
    }

    @Test
    void testMultipleBegin() {
        KvStoreWithTransactions kv = new KvStoreWithTransactions();
        kv.set(1, 3);

        kv.begin();
        kv.set(2, 4);

        kv.begin();
        kv.set(3, 5);

        assert kv.get(1) == 3;
        assert kv.lastestGet(2) == 4;
        assert kv.lastestGet(3) == 5;

        kv.commit();

        assert kv.get(1) == 3;
        assert kv.lastestGet(2) == 4;
        assert kv.get(3) == 5;

        kv.commit();

        assert kv.get(1) == 3;
        assert kv.get(2) == 4;
        assert kv.get(3) == 5;
    }
}
