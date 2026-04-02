package com.mingsha.jvm.gc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GC Collector interface.
 * <p>
 * All garbage collectors must implement this interface.
 *
 * @version 1.0.0
 */
public interface GCCollector {

    /** Logger instance */
    Logger logger = LoggerFactory.getLogger(GCCollector.class);

    /**
     * Performs garbage collection.
     */
    void collect();

    /**
     * Returns bytes reclaimed by last GC.
     *
     * @return reclaimed bytes
     */
    long getReclaimedBytes();

    /**
     * Returns total GC count.
     *
     * @return collection count
     */
    int getCollectionCount();

    /**
     * Returns collector name.
     *
     * @return name
     */
    String getName();
}
