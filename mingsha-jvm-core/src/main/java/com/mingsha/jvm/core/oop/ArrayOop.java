package com.mingsha.jvm.core.oop;

import com.mingsha.jvm.core.constants.JVMConstants;

/**
 * Base class for array types in the JVM.
 */
public abstract class ArrayOop extends OopDesc {
    protected int length;

    public ArrayOop() { super(); }
    public ArrayOop(int length) { super(); this.length = length; }

    public int getLength() { return length; }
    public void setLength(int length) { this.length = length; }

    protected abstract int elementSize();

    @Override
    public int size() {
        int headerSize = JVMConstants.OBJECT_HEADER_SIZE + 4;
        int arraySize = length * elementSize();
        return alignSize(headerSize + arraySize);
    }

    private int alignSize(int size) {
        return (size + JVMConstants.OBJECT_ALIGNMENT - 1) & ~(JVMConstants.OBJECT_ALIGNMENT - 1);
    }
}
