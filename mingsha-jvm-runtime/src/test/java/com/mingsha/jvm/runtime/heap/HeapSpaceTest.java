package com.mingsha.jvm.runtime.heap;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HeapSpaceTest {

    @Test
    void testHeapSpaceCreation() {
        HeapSpace heap = new HeapSpace(1024, 1024 * 1024);
        assertNotNull(heap);
        assertEquals(1024 * 1024, heap.getMaxSize());
    }

    @Test
    void testAllocation() {
        HeapSpace heap = new HeapSpace(1024, 1024 * 1024);
        long addr = heap.allocate(100);
        assertTrue(addr >= 0);
        assertEquals(100, heap.getUsedSize());
    }

    @Test
    void testAllocationCount() {
        HeapSpace heap = new HeapSpace(1024, 1024 * 1024);
        heap.allocate(100);
        heap.allocate(200);
        assertEquals(2, heap.getAllocationCount());
    }

    @Test
    void testFreeSize() {
        HeapSpace heap = new HeapSpace(1024, 1024 * 1024);
        long initialFree = heap.getFreeSize();
        heap.allocate(1000);
        assertEquals(initialFree - 1000, heap.getFreeSize());
    }

    @Test
    void testAllocationFailure() {
        HeapSpace heap = new HeapSpace(1024, 100);
        long addr = heap.allocate(200);
        assertEquals(-1, addr);
    }

    @Test
    void testFree() {
        HeapSpace heap = new HeapSpace(1024, 1024 * 1024);
        long addr = heap.allocate(100);
        heap.free(addr, 100);
        assertEquals(0, heap.getUsedSize());
    }

    @Test
    void testResetAfterGc() {
        HeapSpace heap = new HeapSpace(1024, 1024 * 1024);
        heap.allocate(500);
        heap.resetAfterGc(500);
        assertEquals(0, heap.getUsedSize());
        assertEquals(1, heap.getGcCount());
    }

    @Test
    void testUsageRatio() {
        HeapSpace heap = new HeapSpace(1024, 1024 * 1024);
        assertEquals(0.0f, heap.getUsageRatio());
        heap.allocate(512 * 1024);
        assertEquals(0.5f, heap.getUsageRatio());
    }

    @Test
    void testToString() {
        HeapSpace heap = new HeapSpace(1024, 1024 * 1024);
        String str = heap.toString();
        assertNotNull(str);
        assertTrue(str.contains("HeapSpace"));
    }
}