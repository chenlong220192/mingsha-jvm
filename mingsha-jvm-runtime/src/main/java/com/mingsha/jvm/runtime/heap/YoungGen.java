package com.mingsha.jvm.runtime.heap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the young generation space in generational GC.
 * <p>
 * Young generation consists of:
 * <ul>
 *   <li>Eden: where new objects are allocated</li>
 *   <li>Survivor spaces (From/To): where surviving objects are copied during minor GC</li>
 * </ul>
 *
 * @version 1.0.0
 */
public class YoungGen {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(YoungGen.class);

    /** Eden space size in bytes */
    private final long edenSize;

    /** Survivor space size in bytes (each, for From and To) */
    private final long survivorSize;

    /** Eden space used in bytes */
    private long edenUsed;

    /** Survivor space used in bytes */
    private long survivorUsed;

    /**
     * Constructor with sizes.
     *
     * @param edenSize Eden size in bytes
     * @param survivorSize each Survivor space size in bytes
     */
    public YoungGen(long edenSize, long survivorSize) {
        this.edenSize = edenSize;
        this.survivorSize = survivorSize;
        this.edenUsed = 0;
        this.survivorUsed = 0;
        logger.info("YoungGen initialized: eden={}, survivor={}", edenSize, survivorSize);
    }

    /** @return Eden size in bytes */
    public long getEdenSize() { return edenSize; }

    /** @return Survivor space size in bytes */
    public long getSurvivorSize() { return survivorSize; }

    /** @return Eden used space in bytes */
    public long getEdenUsed() { return edenUsed; }

    /** @return Survivor used space in bytes */
    public long getSurvivorUsed() { return survivorUsed; }

    /** @return total used space in young gen */
    public long getTotalUsed() { return edenUsed + survivorUsed; }

    /**
     * Allocates space in Eden.
     *
     * @param size bytes to allocate
     * @return address in Eden, or -1 if insufficient
     */
    public synchronized long allocateInEden(long size) {
        if (edenUsed + size > edenSize) {
            logger.debug("Eden allocation failed: requested={}, available={}", size, edenSize - edenUsed);
            return -1;
        }
        long addr = edenUsed;
        edenUsed += size;
        logger.trace("Allocated {} bytes in Eden at {}", size, addr);
        return addr;
    }

    /**
     * Copies surviving objects from Eden to Survivor space.
     *
     * @param size bytes to copy
     */
    public synchronized void copyToSurvivor(long size) {
        if (survivorUsed + size > survivorSize) {
            logger.debug("Survivor space full, needs promotion");
            return;
        }
        survivorUsed += size;
    }

    /**
     * Resets young generation after minor GC.
     */
    public synchronized void resetAfterGc() {
        edenUsed = 0;
        survivorUsed = 0;
        logger.debug("YoungGen reset after GC");
    }

    @Override
    public String toString() {
        return String.format("YoungGen{eden=%d/%d, survivor=%d/%d}",
                edenUsed, edenSize, survivorUsed, survivorSize);
    }
}
