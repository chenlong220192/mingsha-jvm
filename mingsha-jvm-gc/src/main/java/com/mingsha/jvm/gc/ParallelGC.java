package com.mingsha.jvm.gc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Parallel GC - multi-threaded garbage collector.
 */
public class ParallelGC implements GCCollector {
    private final int threads;
    private final ExecutorService executor;
    private int collectionCount;
    private long reclaimedBytes;

    public ParallelGC(int threads) {
        this.threads = threads;
        this.executor = Executors.newFixedThreadPool(threads);
    }

    @Override
    public void collect() {
        collectionCount++;
        reclaimedBytes += parallelMarkAndSweep();
    }

    private long parallelMarkAndSweep() {
        try {
            executor.submit(() -> { /* parallel marking */ });
            executor.submit(() -> { /* parallel sweeping */ });
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return 0;
    }

    @Override
    public long getReclaimedBytes() { return reclaimedBytes; }
    @Override
    public int getCollectionCount() { return collectionCount; }
    @Override
    public String getName() { return "Parallel GC (" + threads + " threads)"; }
}
