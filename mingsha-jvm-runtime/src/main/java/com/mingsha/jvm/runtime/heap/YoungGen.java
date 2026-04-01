package com.mingsha.jvm.runtime.heap;

/**
 * Represents the young generation space (Eden + Survivor).
 */
public class YoungGen {
    private final long edenSize;
    private final long survivorSize;
    private long edenUsed;
    private long survivorUsed;

    public YoungGen(long edenSize, long survivorSize) {
        this.edenSize = edenSize;
        this.survivorSize = survivorSize;
        this.edenUsed = 0;
        this.survivorUsed = 0;
    }

    public long getEdenSize() { return edenSize; }
    public long getSurvivorSize() { return survivorSize; }
    public long getEdenUsed() { return edenUsed; }
    public long getSurvivorUsed() { return survivorUsed; }

    public synchronized long allocateInEden(long size) {
        if (edenUsed + size > edenSize) return -1;
        long addr = edenUsed;
        edenUsed += size;
        return addr;
    }

    public synchronized void resetAfterGc() {
        edenUsed = 0;
        survivorUsed = 0;
    }
}
