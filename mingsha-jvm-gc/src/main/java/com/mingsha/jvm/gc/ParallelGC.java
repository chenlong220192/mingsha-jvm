package com.mingsha.jvm.gc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Parallel GC - multi-threaded garbage collector.
 * <p>
 * Uses multiple threads for mark and sweep phases.
 * Best for multi-core systems with large heaps.
 *
 * @version 1.0.0
 */
public class ParallelGC implements GCCollector {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(ParallelGC.class);

    /** Number of GC threads */
    private final int threads;

    /** Thread pool for parallel GC */
    private final ExecutorService executor;

    /** Collection count */
    private int collectionCount;

    /** Total bytes reclaimed */
    private long reclaimedBytes;

    /** GC start time */
    private long gcStartTime;

    /**
     * Constructor with thread count.
     *
     * @param threads number of GC threads
     */
    public ParallelGC(int threads) {
        this.threads = threads;
        this.executor = Executors.newFixedThreadPool(threads);
        logger.info("ParallelGC created with {} threads", threads);
    }

    @Override
    public void collect() {
        logger.info("ParallelGC starting collection #{}", collectionCount + 1);
        gcStartTime = System.nanoTime();

        // Parallel mark
        logger.debug("ParallelGC mark phase with {} threads", threads);
        parallelMark();

        // Parallel sweep
        logger.debug("ParallelGC sweep phase with {} threads", threads);
        parallelSweep();

        // Shutdown executor
        executor.shutdown();

        collectionCount++;

        long duration = System.nanoTime() - gcStartTime;
        logger.info("ParallelGC collection #{} completed in {}ms",
                collectionCount, duration / 1_000_000);
    }

    private void parallelMark() {
        try {
            for (int i = 0; i < threads; i++) {
                final int threadId = i;
                executor.submit(() -> {
                    logger.trace("ParallelGC mark thread {} started", threadId);
                    // Simulate marking work
                    logger.trace("ParallelGC mark thread {} completed", threadId);
                });
            }
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("ParallelGC mark phase interrupted");
        }
    }

    private void parallelSweep() {
        try {
            for (int i = 0; i < threads; i++) {
                final int threadId = i;
                executor.submit(() -> {
                    logger.trace("ParallelGC sweep thread {} started", threadId);
                    logger.trace("ParallelGC sweep thread {} completed", threadId);
                });
            }
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("ParallelGC sweep phase interrupted");
        }
    }

    @Override
    public long getReclaimedBytes() { return reclaimedBytes; }

    @Override
    public int getCollectionCount() { return collectionCount; }

    @Override
    public String getName() { return "Parallel GC (" + threads + " threads)"; }
}
