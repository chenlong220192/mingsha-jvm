package com.mingsha.jvm.gc;

/**
 * GC Collector interface.
 */
public interface GCCollector {
    void collect();
    long getReclaimedBytes();
    int getCollectionCount();
    String getName();
}
