package com.mingsha.jvm.runtime.heap;

/**
 * Object header for heap objects.
 */
public class ObjectHeader {
    public static final int MARK_WORD_SIZE = 8;
    public static final int KLASS_POINTER_SIZE = 8;
    public static final int OBJECT_HEADER_SIZE = 16;

    private long markWord;
    private long klassPointer;

    public ObjectHeader() { this.markWord = 0; this.klassPointer = 0; }
    public ObjectHeader(long markWord, long klassPointer) { this.markWord = markWord; this.klassPointer = klassPointer; }

    public long getMarkWord() { return markWord; }
    public void setMarkWord(long markWord) { this.markWord = markWord; }
    public long getKlassPointer() { return klassPointer; }
    public void setKlassPointer(long klassPointer) { this.klassPointer = klassPointer; }
    public int identityHashCode() { return (int) (markWord ^ (markWord >>> 32)); }
}
