package com.mingsha.jvm.runtime.heap;

/**
 * Represents the JVM heap space for object allocation.
 */
public class HeapSpace {
    private final long maxSize;
    private long usedSize;

    public HeapSpace(long initialSize, long maxSize) {
        this.maxSize = maxSize;
        this.usedSize = 0;
    }

    public long getMaxSize() { return maxSize; }
    public long getUsedSize() { return usedSize; }
    public long getFreeSize() { return maxSize - usedSize; }
    public float getUsageRatio() { return (float) usedSize / maxSize; }

    public synchronized long allocate(long size) {
        if (usedSize + size > maxSize) return -1;
        long addr = usedSize;
        usedSize += size;
        return addr;
    }

    public synchronized void free(long addr, long size) {
        usedSize -= size;
    }
}
