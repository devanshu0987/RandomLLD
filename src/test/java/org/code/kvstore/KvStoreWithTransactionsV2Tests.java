package org.code.kvstore;

import org.coda.kvstore.KvStoreWithTransactions;
import org.coda.kvstore.KvStoreWithTransactionsV2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KvStoreWithTransactionsV2Tests {
    @Test
    public void testKvStore() {
        KvStoreWithTransactionsV2 kv = new KvStoreWithTransactionsV2();
        kv.set(1, 3);

        assertEquals(kv.get(1), 3);
        assertEquals(kv.get(2), null);
    }

    @Test
    public void testKvStoreSingleTransaction() {
        KvStoreWithTransactionsV2 kv = new KvStoreWithTransactionsV2();
        kv.set(1, 3);

        kv.begin();
        kv.set(2, 4);
        assert kv.get(1) == 3;
        assert kv.get(2) == 4;
        kv.commit();

        assert kv.get(1) == 3;
        assert kv.get(2) == 4;
    }

    @Test
    public void testKvStoreSingleTransactionRollback() {
        KvStoreWithTransactionsV2 kv = new KvStoreWithTransactionsV2();
        kv.set(1, 3);

        kv.begin();
        kv.set(2, 4);
        assert kv.get(1) == 3;
        assert kv.get(2) == 4;
        kv.rollback();

        assert kv.get(1) == 3;
        assert kv.get(2) == null;
    }

    @Test
    void testMultipleBegin() {
        KvStoreWithTransactionsV2 kv = new KvStoreWithTransactionsV2();
        kv.set(1, 3);

        kv.begin();
        kv.set(2, 4);

        kv.begin();
        kv.set(3, 5);

        assert kv.get(1) == 3;
        assert kv.get(2) == 4;
        assert kv.get(3) == 5;

        kv.commit();

        assert kv.get(1) == 3;
        assert kv.get(2) == 4;
        assert kv.get(3) == 5;

        kv.commit();

        assert kv.get(1) == 3;
        assert kv.get(2) == 4;
        assert kv.get(3) == 5;

        // all transactions are already committed. No impact.
        kv.rollback();

        assert kv.get(1) == 3;
        assert kv.get(2) == 4;
        assert kv.get(3) == 5;
    }
}
