package com.mingsha.jvm.core.utils;

/**
 * Utility class for reading class file bytecode.
 */
public class BytecodeReader {
    private final byte[] data;
    private int position;

    public BytecodeReader(byte[] data) {
        this.data = data;
        this.position = 0;
    }

    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }
    public boolean hasRemaining() { return position < data.length; }
    public int remaining() { return data.length - position; }

    public void skip(int bytes) { position += bytes; }

    public byte readByte() { return data[position++]; }
    public int readUnsignedByte() { return data[position++] & 0xFF; }

    public int readShort() {
        int b1 = data[position++] & 0xFF;
        int b2 = data[position++] & 0xFF;
        return (short) ((b1 << 8) | b2);
    }

    public int readUnsignedShort() {
        int b1 = data[position++] & 0xFF;
        int b2 = data[position++] & 0xFF;
        return (b1 << 8) | b2;
    }

    public int readInt() {
        int b1 = data[position++] & 0xFF;
        int b2 = data[position++] & 0xFF;
        int b3 = data[position++] & 0xFF;
        int b4 = data[position++] & 0xFF;
        return (b1 << 24) | (b2 << 16) | (b3 << 8) | b4;
    }

    public long readLong() {
        int b1 = data[position++] & 0xFF;
        int b2 = data[position++] & 0xFF;
        int b3 = data[position++] & 0xFF;
        int b4 = data[position++] & 0xFF;
        int b5 = data[position++] & 0xFF;
        int b6 = data[position++] & 0xFF;
        int b7 = data[position++] & 0xFF;
        int b8 = data[position++] & 0xFF;
        return ((long) b1 << 56) | ((long) b2 << 48) | ((long) b3 << 40) | ((long) b4 << 32)
                | ((long) b5 << 24) | ((long) b6 << 16) | ((long) b7 << 8) | b8;
    }
}
