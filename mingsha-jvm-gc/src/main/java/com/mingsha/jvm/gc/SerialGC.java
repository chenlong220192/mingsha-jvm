package com.mingsha.jvm.gc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Serial GC - single-threaded garbage collector.
 * <p>
 * Uses mark-and-sweep algorithm with stop-the-world pauses.
 * Suitable for single-threaded environments or small heaps.
 *
 * @version 1.0.0
 */
public class SerialGC implements GCCollector {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(SerialGC.class);

    /** Total collection count */
    private int collectionCount;

    /** Total bytes reclaimed */
    private long reclaimedBytes;

    /** GC start time */
    private long gcStartTime;

    @Override
    public void collect() {
        logger.info("SerialGC starting collection #{}", collectionCount + 1);
        gcStartTime = System.nanoTime();

        // Phase 1: Mark - traverse heap from GC roots
        logger.debug("SerialGC mark phase");
        mark();

        // Phase 2: Sweep - collect unreachable objects
        logger.debug("SerialGC sweep phase");
        long freed = sweep();

        // Phase 3: Compact (optional)
        logger.debug("SerialGC compact phase");
        compact();

        collectionCount++;
        reclaimedBytes += freed;

        long duration = System.nanoTime() - gcStartTime;
        logger.info("SerialGC collection #{} completed: reclaimed={} bytes, duration={}ms",
                collectionCount, freed, duration / 1_000_000);
    }

    /**
     * Mark phase - traverse from GC roots.
     */
    private void mark() {
        logger.trace("Marking reachable objects from GC roots");
        // In real implementation: traverse heap, mark live objects
    }

    /**
     * Sweep phase - collect unmarked objects.
     *
     * @return bytes freed
     */
    private long sweep() {
        logger.trace("Sweeping unreachable objects");
        // Simulation: return 0
        return 0;
    }

    /**
     * Compact phase - reduce fragmentation.
     */
    private void compact() {
        logger.trace("Compacting heap");
        // Simulation: no-op
    }

    @Override
    public long getReclaimedBytes() {
        return reclaimedBytes;
    }

    @Override
    public int getCollectionCount() {
        return collectionCount;
    }

    @Override
    public String getName() {
        return "Serial GC";
    }
}
