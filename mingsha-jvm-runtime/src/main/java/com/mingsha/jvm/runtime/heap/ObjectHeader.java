package com.mingsha.jvm.runtime.heap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Object header for heap objects.
 * <p>
 * Layout (64-bit JVM):
 * <ul>
 *   <li>Mark word: 8 bytes - hash, GC age, lock state</li>
 *   <li>Klass pointer: 8 bytes - pointer to class metadata</li>
 * </ul>
 *
 * @version 1.0.0
 */
public class ObjectHeader {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(ObjectHeader.class);

    /** Mark word size in bytes */
    public static final int MARK_WORD_SIZE = 8;

    /** Klass pointer size in bytes */
    public static final int KLASS_POINTER_SIZE = 8;

    /** Object header size (mark + klass) */
    public static final int OBJECT_HEADER_SIZE = MARK_WORD_SIZE + KLASS_POINTER_SIZE;

    /** Mark word value */
    private long markWord;

    /** Klass pointer value */
    private long klassPointer;

    /** Default constructor */
    public ObjectHeader() {
        this.markWord = 0;
        this.klassPointer = 0;
    }

    /**
     * Constructor with values.
     *
     * @param markWord mark word value
     * @param klassPointer klass pointer value
     */
    public ObjectHeader(long markWord, long klassPointer) {
        this.markWord = markWord;
        this.klassPointer = klassPointer;
    }

    /** @return mark word value */
    public long getMarkWord() { return markWord; }

    /** @param markWord new mark word */
    public void setMarkWord(long markWord) {
        this.markWord = markWord;
        logger.trace("Mark word set: 0x{}", Long.toHexString(markWord));
    }

    /** @return klass pointer value */
    public long getKlassPointer() { return klassPointer; }

    /** @param klassPointer new klass pointer */
    public void setKlassPointer(long klassPointer) {
        this.klassPointer = klassPointer;
        logger.trace("Klass pointer set: 0x{}", Long.toHexString(klassPointer));
    }

    /**
     * Returns identity hash code.
     * @return hash code derived from mark word
     */
    public int identityHashCode() {
        return (int) (markWord ^ (markWord >>> 32));
    }

    @Override
    public String toString() {
        return String.format("ObjectHeader{mark=0x%x, klass=0x%x}",
                markWord, klassPointer);
    }
}
