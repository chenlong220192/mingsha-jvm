package com.mingsha.jvm.gc;

/**
 * Serial GC - single-threaded garbage collector.
 */
public class SerialGC implements GCCollector {
    private int collectionCount;
    private long reclaimedBytes;

    @Override
    public void collect() {
        collectionCount++;
        reclaimedBytes += doMarkAndSweep();
    }

    private long doMarkAndSweep() {
        long freed = 0;
        // Mark phase: traverse heap to find live objects
        // Sweep phase: collect unmarked objects
        // For simulation, just reset counters
        return freed;
    }

    @Override
    public long getReclaimedBytes() { return reclaimedBytes; }
    @Override
    public int getCollectionCount() { return collectionCount; }
    @Override
    public String getName() { return "Serial GC"; }
}
