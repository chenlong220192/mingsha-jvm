package com.mingsha.jvm.core.oop;

import com.mingsha.jvm.core.constants.JVMConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents an instance of java.lang.Object.
 * <p>
 * InstanceOop is the runtime representation of a Java object.
 * It contains the object header plus space for instance fields.
 *
 * @version 1.0.0
 * @see OopDesc for base class
 */
public class InstanceOop extends OopDesc {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(InstanceOop.class);

    /** Array to store instance field values */
    private Object[] fields;

    /** Default constructor - creates InstanceOop with zero fields */
    public InstanceOop() {
        super();
        this.fields = new Object[0];
        logger.trace("Created InstanceOop with 0 fields");
    }

    /**
     * Constructor with specified field count.
     *
     * @param numFields the number of instance fields
     */
    public InstanceOop(int numFields) {
        super();
        this.fields = new Object[numFields];
        logger.trace("Created InstanceOop with {} fields", numFields);
    }

    /** @return the fields array */
    public Object[] getFields() { return fields; }

    /** @param fields the new fields array */
    public void setFields(Object[] fields) { this.fields = fields; }

    /**
     * Gets the value of a field at the given index.
     *
     * @param index the field index
     * @return the field value
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public Object getField(int index) {
        if (index < 0 || index >= fields.length) {
            throw new IndexOutOfBoundsException("Field index out of bounds: " + index);
        }
        return fields[index];
    }

    /**
     * Sets the value of a field at the given index.
     *
     * @param index the field index
     * @param value the new field value
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public void setField(int index, Object value) {
        if (index < 0 || index >= fields.length) {
            throw new IndexOutOfBoundsException("Field index out of bounds: " + index);
        }
        fields[index] = value;
        logger.trace("Set field[{}] = {}", index, value);
    }

    /**
     * Returns the total size of this object in bytes.
     * Size = header size + (field count * alignment)
     *
     * @return the object size in bytes
     */
    @Override
    public int size() {
        int fieldSize = fields.length * JVMConstants.OBJECT_ALIGNMENT;
        return JVMConstants.OBJECT_HEADER_SIZE + fieldSize;
    }

    /** @return the number of fields */
    public int getFieldCount() { return fields.length; }

    @Override
    public String toString() {
        return String.format("InstanceOop{fieldCount=%d, size=%d}", fields.length, size());
    }
}
