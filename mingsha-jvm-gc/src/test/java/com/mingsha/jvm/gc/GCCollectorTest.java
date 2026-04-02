package com.mingsha.jvm.gc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GCCollectorTest {

    @Test
    void testSerialGCCollect() {
        SerialGC gc = new SerialGC();
        assertDoesNotThrow(() -> gc.collect());
        assertTrue(gc.getReclaimedBytes() >= 0);
        assertTrue(gc.getCollectionCount() >= 1);
    }

    @Test
    void testSerialGCName() {
        SerialGC gc = new SerialGC();
        assertEquals("Serial GC", gc.getName());
    }

    @Test
    void testParallelGCName() {
        ParallelGC gc = new ParallelGC(2);
        assertEquals("Parallel GC (2 threads)", gc.getName());
    }

    @Test
    void testMultipleCollections() {
        SerialGC gc = new SerialGC();
        gc.collect();
        gc.collect();
        assertEquals(2, gc.getCollectionCount());
    }
}