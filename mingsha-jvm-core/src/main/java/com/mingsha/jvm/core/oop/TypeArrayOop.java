package com.mingsha.jvm.core.oop;

import com.mingsha.jvm.core.constants.JVMConstants;

/**
 * Represents an array of primitive types.
 */
public class TypeArrayOop extends ArrayOop {
    public enum ElementType { T_BOOLEAN(1), T_CHAR(2), T_FLOAT(4), T_DOUBLE(8), T_BYTE(1), T_SHORT(2), T_INT(4), T_LONG(8);
        private final int size;
        ElementType(int size) { this.size = size; }
        public int getSize() { return size; }
    }

    private ElementType elementType;
    private byte[] data;
    private char[] charData;
    private short[] shortData;
    private int[] intData;
    private long[] longData;
    private float[] floatData;
    private double[] doubleData;

    public TypeArrayOop(ElementType elementType, int length) {
        super(length);
        this.elementType = elementType;
        initializeArray();
    }

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

    @Override
    protected int elementSize() { return elementType.getSize(); }

    public int getInt(int index) { return intData[index]; }
    public void setInt(int index, int value) { intData[index] = value; }
    public long getLong(int index) { return longData[index]; }
    public void setLong(int index, long value) { longData[index] = value; }
}
