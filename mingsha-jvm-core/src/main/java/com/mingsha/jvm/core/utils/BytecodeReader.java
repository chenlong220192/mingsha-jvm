package com.mingsha.jvm.core.utils;

import java.io.IOException;

/**
 * Utility class for reading JVM bytecode from class files.
 * <p>
 * Provides methods for reading various data types from bytecode streams
 * in big-endian format (network byte order, as used by JVM).
 *
 * @version 1.0.0
 */
public class BytecodeReader {

    /** The bytecode data buffer */
    private final byte[] data;

    /** Current read position */
    private int position;

    /**
     * Constructor with bytecode array.
     *
     * @param data the bytecode to read
     */
    public BytecodeReader(byte[] data) {
        this.data = data;
        this.position = 0;
    }

    /** @return current read position */
    public int getPosition() { return position; }

    /** @param position new read position */
    public void setPosition(int position) { this.position = position; }

    /** @return true if more bytes available */
    public boolean hasRemaining() { return position < data.length; }

    /** @return number of bytes remaining */
    public int remaining() { return data.length - position; }

    /**
     * Skip bytes forward.
     *
     * @param bytes number of bytes to skip
     */
    public void skip(int bytes) { position += bytes; }

    /**
     * Read a signed byte.
     *
     * @return signed byte value
     */
    public byte readByte() { return data[position++]; }

    /**
     * Read an unsigned byte.
     *
     * @return unsigned byte as int
     */
    public int readUnsignedByte() { return data[position++] & 0xFF; }

    /**
     * Read a signed short (16-bit).
     *
     * @return short value
     */
    public int readShort() {
        int b1 = data[position++] & 0xFF;
        int b2 = data[position++] & 0xFF;
        return (short) ((b1 << 8) | b2);
    }

    /**
     * Read an unsigned short (16-bit).
     *
     * @return unsigned short as int
     */
    public int readUnsignedShort() {
        int b1 = data[position++] & 0xFF;
        int b2 = data[position++] & 0xFF;
        return (b1 << 8) | b2;
    }

    /**
     * Read a char (UTF-16).
     *
     * @return char value
     */
    public char readChar() {
        return (char) readUnsignedShort();
    }

    /**
     * Read a signed int (32-bit).
     *
     * @return int value
     */
    public int readInt() {
        int b1 = data[position++] & 0xFF;
        int b2 = data[position++] & 0xFF;
        int b3 = data[position++] & 0xFF;
        int b4 = data[position++] & 0xFF;
        return (b1 << 24) | (b2 << 16) | (b3 << 8) | b4;
    }

    /**
     * Read an unsigned int (32-bit).
     *
     * @return unsigned int as long
     */
    public long readUnsignedInt() {
        return readInt() & 0xFFFFFFFFL;
    }

    /**
     * Read a signed long (64-bit).
     *
     * @return long value
     */
    public long readLong() {
        long b1 = data[position++] & 0xFF;
        long b2 = data[position++] & 0xFF;
        long b3 = data[position++] & 0xFF;
        long b4 = data[position++] & 0xFF;
        long b5 = data[position++] & 0xFF;
        long b6 = data[position++] & 0xFF;
        long b7 = data[position++] & 0xFF;
        long b8 = data[position++] & 0xFF;
        return (b1 << 56) | (b2 << 48) | (b3 << 40) | (b4 << 32)
                | (b5 << 24) | (b6 << 16) | (b7 << 8) | b8;
    }

    /**
     * Read a float (32-bit IEEE 754).
     *
     * @return float value
     */
    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }

    /**
     * Read a double (64-bit IEEE 754).
     *
     * @return double value
     */
    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    /**
     * Read a MIPS-style unsigned int (for constant pool indices).
     *
     * @return unsigned int value
     */
    public int readU1() { return readUnsignedByte(); }
    public int readU2() { return readUnsignedShort(); }
    public int readU4() { return readInt(); }

    /**
     * Read a UTF-8 string of given length.
     *
     * @param length string byte length
     * @return UTF-8 string
     */
    public String readUtf8(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append((char) data[position++]);
        }
        return sb.toString();
    }

    /**
     * Read raw bytes.
     *
     * @param length number of bytes to read
     * @return byte array
     */
    public byte[] readBytes(int length) {
        byte[] bytes = new byte[length];
        System.arraycopy(data, position, bytes, 0, length);
        position += length;
        return bytes;
    }

    /**
     * Mark current position for reset.
     *
     * @return current position
     */
    public int mark() { return position; }

    /**
     * Reset to previously marked position.
     *
     * @param markPosition position from mark()
     */
    public void reset(int markPosition) { this.position = markPosition; }
}
