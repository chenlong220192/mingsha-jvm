package com.mingsha.jvm.core.oop;

import com.mingsha.jvm.core.constants.JVMConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for array types in the JVM.
 * <p>
 * All arrays in the JVM are represented as ArrayOop subclasses.
 * Array objects have an additional length field after the object header.
 *
 * @version 1.0.0
 * @see TypeArrayOop for primitive type arrays
 * @see ObjArrayOop for object reference arrays
 */
public abstract class ArrayOop extends OopDesc {

    /** Logger instance */
    protected static final Logger logger = LoggerFactory.getLogger(ArrayOop.class);

    /** Array length (number of elements) */
    protected int length;

    /** Default constructor */
    public ArrayOop() {
        super();
        this.length = 0;
    }

    /**
     * Constructor with length.
     *
     * @param length the array length
     */
    public ArrayOop(int length) {
        super();
        this.length = length;
        logger.trace("Created ArrayOop with length {}", length);
    }

    /** @return the array length */
    public int getLength() { return length; }

    /** @param length the new array length */
    public void setLength(int length) { this.length = length; }

    /**
     * Returns the size of each element in bytes.
     *
     * @return element size in bytes
     */
    protected abstract int elementSize();

    /**
     * Returns the total size of this array in bytes.
     * Size = header (16) + length field (4) + array data (aligned)
     *
     * @return the array size in bytes
     */
    @Override
    public int size() {
        int headerSize = JVMConstants.OBJECT_HEADER_SIZE + 4; // +4 for length field
        int arraySize = length * elementSize();
        return alignSize(headerSize + arraySize);
    }

    /**
     * Aligns size to 8-byte boundary.
     *
     * @param size the raw size
     * @return the aligned size
     */
    private int alignSize(int size) {
        return (size + JVMConstants.OBJECT_ALIGNMENT - 1) & ~(JVMConstants.OBJECT_ALIGNMENT - 1);
    }

    @Override
    public String toString() {
        return String.format("ArrayOop{length=%d, elementSize=%d, size=%d}",
                length, elementSize(), size());
    }
}
