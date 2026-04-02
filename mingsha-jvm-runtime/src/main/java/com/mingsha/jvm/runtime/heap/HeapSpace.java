package com.mingsha.jvm.runtime.heap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the JVM heap space for object allocation.
 * <p>
 * The heap is the runtime data area where object instances are allocated.
 * It is shared among all threads.
 *
 * @version 1.0.0
 */
public class HeapSpace {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(HeapSpace.class);

    /** Maximum heap size in bytes */
    private final long maxSize;

    /** Currently used heap space in bytes */
    private long usedSize;

    /** Number of allocations performed */
    private long allocationCount;

    /** Number of garbage collections performed */
    private long gcCount;

    /**
     * Constructor with sizes.
     *
     * @param initialSize initial heap size (currently unused, same as max)
     * @param maxSize maximum heap size in bytes
     */
    public HeapSpace(long initialSize, long maxSize) {
        this.maxSize = maxSize;
        this.usedSize = 0;
        this.allocationCount = 0;
        this.gcCount = 0;
        logger.info("HeapSpace initialized: maxSize={} bytes", maxSize);
    }

    /** @return maximum heap size in bytes */
    public long getMaxSize() { return maxSize; }

    /** @return currently used heap space in bytes */
    public long getUsedSize() { return usedSize; }

    /** @return free heap space in bytes */
    public long getFreeSize() { return maxSize - usedSize; }

    /** @return heap usage ratio (0.0 to 1.0) */
    public float getUsageRatio() { return (float) usedSize / maxSize; }

    /** @return number of allocations performed */
    public long getAllocationCount() { return allocationCount; }

    /** @return number of garbage collections performed */
    public long getGcCount() { return gcCount; }

    /**
     * Allocates memory from the heap.
     * <p>
     * This is a simple linear allocator. Real JVMs use more sophisticated
     * allocation strategies like TLABs (Thread-Local Allocation Buffers).
     *
     * @param size number of bytes to allocate
     * @return allocated address, or -1 if insufficient memory
     */
    public synchronized long allocate(long size) {
        if (usedSize + size > maxSize) {
            logger.warn("Heap allocation failed: requested={}, available={}", size, getFreeSize());
            return -1;
        }
        long addr = usedSize;
        usedSize += size;
        allocationCount++;
        logger.trace("Allocated {} bytes at address {}", size, addr);
        return addr;
    }

    /**
     * Frees memory at the specified address.
     * <p>
     * Note: In a real GC, objects are not freed individually.
     * This method exists for simulation purposes.
     *
     * @param addr memory address to free
     * @param size size of memory to free
     */
    public synchronized void free(long addr, long size) {
        if (addr >= 0 && addr < maxSize) {
            usedSize -= size;
            logger.trace("Freed {} bytes at address {}", size, addr);
        }
    }

    /**
     * Resets heap after garbage collection.
     *
     * @param reclaimedBytes bytes reclaimed by GC
     */
    public synchronized void resetAfterGc(long reclaimedBytes) {
        usedSize -= reclaimedBytes;
        gcCount++;
        logger.info("GC completed: reclaimed={} bytes, used={} bytes, gcCount={}",
                reclaimedBytes, usedSize, gcCount);
    }

    /**
     * Returns a string representation for debugging.
     */
    @Override
    public String toString() {
        return String.format("HeapSpace{maxSize=%d, used=%d, free=%d, usage=%.1f%%, allocs=%d, gcs=%d}",
                maxSize, usedSize, getFreeSize(), getUsageRatio() * 100, allocationCount, gcCount);
    }
}
