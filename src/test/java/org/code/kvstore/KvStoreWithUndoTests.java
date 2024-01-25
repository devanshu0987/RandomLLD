package org.code.kvstore;

import org.coda.kvstore.KvStoreWithUndo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KvStoreWithUndoTests {
    @Test
    public void testHappyPath() {
        KvStoreWithUndo kv = new KvStoreWithUndo();
        kv.put("a", "1");
        assertEquals(kv.get("a"), "1");
    }

    @Test
    public void testHappyPathUndo() {
        KvStoreWithUndo kv = new KvStoreWithUndo();
        kv.put("a", "1");
        assertEquals(kv.get("a"), "1");
        kv.undo();
        assertEquals(kv.get("a"), null);
    }

    @Test
    public void testHappyPathRedo() {
        KvStoreWithUndo kv = new KvStoreWithUndo();
        kv.put("a", "1");
        kv.undo();
        kv.redo();
        assertEquals(kv.get("a"), "1");
    }

    @Test
    public void testMultiplePuts() {
        KvStoreWithUndo kv = new KvStoreWithUndo();
        kv.put("a", "1");
        kv.undo();
        kv.redo();
        kv.put("a", "2");
        kv.undo();
        kv.redo();
        assertEquals(kv.get("a"), "2");
    }

    @Test
    public void testMultipleUndo() {
        KvStoreWithUndo kv = new KvStoreWithUndo();
        kv.put("a", "1");
        kv.put("a", "2");
        kv.put("a", "3");
        kv.undo();
        assertEquals(kv.get("a"), "2");
        kv.undo();
        assertEquals(kv.get("a"), "1");
        kv.undo();
        assertEquals(kv.get("a"), null);
    }

    @Test
    public void testMultipleRedo() {
        KvStoreWithUndo kv = new KvStoreWithUndo();
        kv.put("a", "1");
        kv.put("a", "2");
        kv.put("a", "3");
        kv.undo();
        kv.undo();
        kv.undo();
        kv.redo();
        kv.redo();
        kv.redo();
        assertEquals(kv.get("a"), "3");
    }

    @Test
    public void testUndoRedoInLoop() {
        KvStoreWithUndo kv = new KvStoreWithUndo();
        kv.put("a", "1");
        kv.put("a", "2");
        kv.put("a", "3");
        int count = 5;
        while (count-- > 0) {
            kv.undo();
            kv.redo();
        }
        assertEquals(kv.get("a"), "3");
    }

    @Test
    public void testUndoMoreThanOperations() {
        KvStoreWithUndo kv = new KvStoreWithUndo();
        kv.put("a", "1");
        kv.put("a", "2");
        kv.put("a", "3");
        int count = 5;
        while (count-- > 0) {
            kv.undo();
        }
        assertEquals(kv.get("a"), null);
    }

    @Test
    public void testRedoMoreThanOperations() {
        KvStoreWithUndo kv = new KvStoreWithUndo();
        kv.put("a", "1");
        kv.put("a", "2");
        kv.put("a", "3");
        int count = 5;
        while (count-- > 0) {
            kv.undo();
        }
        count = 5;
        while (count-- > 0) {
            kv.redo();
        }
        assertEquals(kv.get("a"), "3");
    }
}
