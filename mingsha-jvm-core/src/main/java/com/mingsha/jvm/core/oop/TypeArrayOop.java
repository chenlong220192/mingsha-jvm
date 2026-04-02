package com.mingsha.jvm.core.oop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents an array of primitive types.
 * <p>
 * TypeArrayOop handles arrays of primitive types: byte, char, short, int, long, float, double.
 * Each element type has a fixed size as defined in the ElementType enum.
 *
 * @version 1.0.0
 * @see ArrayOop for base class
 * @see ObjArrayOop for object reference arrays
 */
public class TypeArrayOop extends ArrayOop {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(TypeArrayOop.class);

    /**
     * Enumeration of primitive element types and their sizes in bytes.
     */
    public enum ElementType {
        /** 1 byte */
        T_BOOLEAN(1),
        /** 2 bytes */
        T_CHAR(2),
        /** 4 bytes */
        T_FLOAT(4),
        /** 8 bytes */
        T_DOUBLE(8),
        /** 1 byte */
        T_BYTE(1),
        /** 2 bytes */
        T_SHORT(2),
        /** 4 bytes */
        T_INT(4),
        /** 8 bytes */
        T_LONG(8);

        private final int size;
        ElementType(int size) { this.size = size; }
        /** @return element size in bytes */
        public int getSize() { return size; }
    }

    /** The element type of this array */
    private ElementType elementType;

    /** Internal storage arrays for each primitive type */
    private byte[] data;
    private char[] charData;
    private short[] shortData;
    private int[] intData;
    private long[] longData;
    private float[] floatData;
    private double[] doubleData;

    /**
     * Constructor with element type and length.
     *
     * @param elementType the primitive element type
     * @param length the array length
     */
    public TypeArrayOop(ElementType elementType, int length) {
        super(length);
        this.elementType = elementType;
        initializeArray();
        logger.trace("Created TypeArrayOop with type {} and length {}", elementType, length);
    }

    /** Initializes the appropriate internal array based on element type */
    private void initializeArray() {
        switch (elementType) {
            case T_BOOLEAN, T_BYTE -> data = new byte[length];
            case T_CHAR -> charData = new char[length];
            case T_SHORT -> shortData = new short[length];
            case T_INT -> intData = new int[length];
            case T_LONG -> longData = new long[length];
            case T_FLOAT -> floatData = new float[length];
            case T_DOUBLE -> doubleData = new double[length];
        }
    }

    /** @return element size in bytes */
    @Override
    protected int elementSize() { return elementType.getSize(); }

    /** @return the element type */
    public ElementType getElementType() { return elementType; }

    // Primitive accessors - only valid for appropriate types
    /** @return element type */
    public byte getByte(int index) { return data[index]; }
    /** @param index array index */
    /** @param value byte value */
    public void setByte(int index, byte value) { data[index] = value; }

    /** @return element type */
    public char getChar(int index) { return charData[index]; }
    /** @param index array index */
    /** @param value char value */
    public void setChar(int index, char value) { charData[index] = value; }

    /** @return element type */
    public short getShort(int index) { return shortData[index]; }
    /** @param index array index */
    /** @param value short value */
    public void setShort(int index, short value) { shortData[index] = value; }

    /** @return element type */
    public int getInt(int index) { return intData[index]; }
    /** @param index array index */
    /** @param value int value */
    public void setInt(int index, int value) { intData[index] = value; }

    /** @return element type */
    public long getLong(int index) { return longData[index]; }
    /** @param index array index */
    /** @param value long value */
    public void setLong(int index, long value) { longData[index] = value; }

    /** @return element type */
    public float getFloat(int index) { return floatData[index]; }
    /** @param index array index */
    /** @param value float value */
    public void setFloat(int index, float value) { floatData[index] = value; }

    /** @return element type */
    public double getDouble(int index) { return doubleData[index]; }
    /** @param index array index */
    /** @param value double value */
    public void setDouble(int index, double value) { doubleData[index] = value; }

    @Override
    public String toString() {
        return String.format("TypeArrayOop{type=%s, length=%d, size=%d}",
                elementType, length, size());
    }
}
