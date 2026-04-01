package com.mingsha.jvm.core.oop;

import com.mingsha.jvm.core.constants.JVMConstants;

/**
 * Represents an instance of java.lang.Object.
 */
public class InstanceOop extends OopDesc {
    private Object[] fields = new Object[0];

    public InstanceOop() { super(); }
    public InstanceOop(int numFields) { super(); this.fields = new Object[numFields]; }

    public Object[] getFields() { return fields; }
    public void setFields(Object[] fields) { this.fields = fields; }
    public Object getField(int index) { return fields[index]; }
    public void setField(int index, Object value) { fields[index] = value; }

    @Override
    public int size() {
        return JVMConstants.OBJECT_HEADER_SIZE + fields.length * JVMConstants.OBJECT_ALIGNMENT;
    }
}
