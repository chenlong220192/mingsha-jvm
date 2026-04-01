package com.mingsha.jvm.core.oop;

import com.mingsha.jvm.core.constants.JVMConstants;

/**
 * Represents an array of object references.
 */
public class ObjArrayOop extends ArrayOop {
    private OopDesc[] elements;

    public ObjArrayOop(int length) {
        super(length);
        this.elements = new OopDesc[length];
    }

    @Override
    protected int elementSize() { return JVMConstants.OBJECT_ALIGNMENT; }

    public OopDesc get(int index) { return elements[index]; }
    public void set(int index, OopDesc value) { elements[index] = value; }
}
