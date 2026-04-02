package com.mingsha.jvm.core.oop;

import com.mingsha.jvm.core.constants.JVMConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents an array of object references.
 * <p>
 * ObjArrayOop stores references to other Oop objects.
 * Each element is a pointer (reference) to an object.
 *
 * @version 1.0.0
 * @see ArrayOop for base class
 */
public class ObjArrayOop extends ArrayOop {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(ObjArrayOop.class);

    /** Internal array storing OopDesc references */
    private OopDesc[] elements;

    /**
     * Constructor with length.
     *
     * @param length the array length
     */
    public ObjArrayOop(int length) {
        super(length);
        this.elements = new OopDesc[length];
        logger.trace("Created ObjArrayOop with length {}", length);
    }

    /** @return element size (pointer size) */
    @Override
    protected int elementSize() {
        return JVMConstants.OBJECT_ALIGNMENT; // 8 bytes for reference
    }

    /**
     * Gets the element at the given index.
     *
     * @param index the array index
     * @return the OopDesc reference
     */
    public OopDesc get(int index) {
        return elements[index];
    }

    /**
     * Sets the element at the given index.
     *
     * @param index the array index
     * @param value the OopDesc reference
     */
    public void set(int index, OopDesc value) {
        elements[index] = value;
        logger.trace("Set element[{}] = {}", index, value);
    }

    @Override
    public String toString() {
        return String.format("ObjArrayOop{length=%d, size=%d}", length, size());
    }
}
