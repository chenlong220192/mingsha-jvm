package com.mingsha.jvm.core.oop;

import com.mingsha.jvm.core.constants.JVMConstants;

/**
 * Base class for all Oop (Ordinary Object Pointer) types.
 */
public class OopDesc {
    protected long markWord;
    protected long klassPointer;

    public OopDesc() {
        this.markWord = 0;
        this.klassPointer = 0;
    }

    public long getMarkWord() { return markWord; }
    public void setMarkWord(long markWord) { this.markWord = markWord; }
    public long getKlassPointer() { return klassPointer; }
    public void setKlassPointer(long klassPointer) { this.klassPointer = klassPointer; }
    public int size() { return JVMConstants.OBJECT_HEADER_SIZE; }
    public int identityHashCode() { return (int) (markWord ^ (markWord >>> 32)); }
}
