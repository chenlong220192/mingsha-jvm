package com.mingsha.jvm.core.classfile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConstantPool {

    private static final Logger logger = LoggerFactory.getLogger(ConstantPool.class);

    private final Entry[] entries;

    public ConstantPool(int count) {
        this.entries = new Entry[count];
    }

    public void setEntry(int index, Entry entry) {
        entries[index] = entry;
    }

    public Entry getEntry(int index) {
        if (index > 0 && index < entries.length) {
            return entries[index];
        }
        return null;
    }

    public String getUtf8(int index) {
        if (index <= 0 || index >= entries.length) {
            return null;
        }
        Entry entry = entries[index];
        if (entry == null) {
            return null;
        }
        if (entry.type != EntryType.UTF8) {
            return null;
        }
        return entry.stringValue;
    }

    public String getClassName(int classIndex) {
        Entry entry = getEntry(classIndex);
        if (entry != null && entry.type == EntryType.CLASS) {
            return getUtf8(entry.nameIndex);
        }
        return null;
    }

    public Entry getMethodRef(int index) {
        Entry entry = getEntry(index);
        if (entry != null && entry.type == EntryType.METHODREF) {
            return entry;
        }
        return null;
    }

    public Entry getFieldRef(int index) {
        Entry entry = getEntry(index);
        if (entry != null && entry.type == EntryType.FIELDREF) {
            return entry;
        }
        return null;
    }

    public String getMethodClassName(int methodRefIndex) {
        Entry methodRef = getMethodRef(methodRefIndex);
        if (methodRef != null) {
            return getClassName(methodRef.classIndex);
        }
        return null;
    }

    public String getMethodName(int methodRefIndex) {
        Entry methodRef = getMethodRef(methodRefIndex);
        if (methodRef != null) {
            Entry nameAndType = getEntry(methodRef.nameIndex);
            if (nameAndType != null) {
                return getUtf8(nameAndType.nameIndex);
            }
        }
        return null;
    }

    public String getMethodDescriptor(int methodRefIndex) {
        Entry methodRef = getMethodRef(methodRefIndex);
        if (methodRef != null) {
            Entry nameAndType = getEntry(methodRef.nameIndex);
            if (nameAndType != null) {
                return getUtf8(nameAndType.descriptorIndex);
            }
        }
        return null;
    }

    public String getFieldClassName(int fieldRefIndex) {
        Entry fieldRef = getFieldRef(fieldRefIndex);
        if (fieldRef != null) {
            return getClassName(fieldRef.classIndex);
        }
        return null;
    }

    public String getFieldName(int fieldRefIndex) {
        Entry fieldRef = getFieldRef(fieldRefIndex);
        if (fieldRef != null) {
            Entry nameAndType = getEntry(fieldRef.nameIndex);
            if (nameAndType != null) {
                return getUtf8(nameAndType.nameIndex);
            }
        }
        return null;
    }

    public String getFieldDescriptor(int fieldRefIndex) {
        Entry fieldRef = getFieldRef(fieldRefIndex);
        if (fieldRef != null) {
            Entry nameAndType = getEntry(fieldRef.nameIndex);
            if (nameAndType != null) {
                return getUtf8(nameAndType.descriptorIndex);
            }
        }
        return null;
    }

    public Object getConstant(int index) {
        Entry entry = getEntry(index);
        if (entry == null) {
            return null;
        }
        switch (entry.type) {
            case INTEGER: return entry.valueInt;
            case LONG: return entry.valueLong;
            case FLOAT: return entry.valueFloat;
            case DOUBLE: return entry.valueDouble;
            case STRING: return entry.stringValue;
            default: return null;
        }
    }

    public static class Entry {
        public final EntryType type;
        public final int nameIndex;
        public final int descriptorIndex;
        public final int classIndex;
        public final int valueInt;
        public final long valueLong;
        public final float valueFloat;
        public final double valueDouble;
        public final String stringValue;

        private Entry(EntryType type) {
            this.type = type;
            this.nameIndex = 0;
            this.descriptorIndex = 0;
            this.classIndex = 0;
            this.valueInt = 0;
            this.valueLong = 0;
            this.valueFloat = 0;
            this.valueDouble = 0;
            this.stringValue = null;
        }

        public Entry(EntryType type, int value) {
            this.type = type;
            this.nameIndex = value;
            this.descriptorIndex = 0;
            this.classIndex = 0;
            this.valueInt = 0;
            this.valueLong = 0;
            this.valueFloat = 0;
            this.valueDouble = 0;
            this.stringValue = null;
        }

        public Entry(EntryType type, int classIndex, int nameAndTypeIndex) {
            this.type = type;
            this.classIndex = classIndex;
            this.nameIndex = nameAndTypeIndex;
            this.descriptorIndex = 0;
            this.valueInt = 0;
            this.valueLong = 0;
            this.valueFloat = 0;
            this.valueDouble = 0;
            this.stringValue = null;
        }

        public Entry(EntryType type, long value) {
            this.type = type;
            this.nameIndex = 0;
            this.descriptorIndex = 0;
            this.classIndex = 0;
            this.valueInt = 0;
            this.valueLong = value;
            this.valueFloat = 0;
            this.valueDouble = 0;
            this.stringValue = null;
        }

        public Entry(EntryType type, float value) {
            this.type = type;
            this.nameIndex = 0;
            this.descriptorIndex = 0;
            this.classIndex = 0;
            this.valueInt = 0;
            this.valueLong = 0;
            this.valueFloat = value;
            this.valueDouble = 0;
            this.stringValue = null;
        }

        public Entry(EntryType type, double value) {
            this.type = type;
            this.nameIndex = 0;
            this.descriptorIndex = 0;
            this.classIndex = 0;
            this.valueInt = 0;
            this.valueLong = 0;
            this.valueFloat = 0;
            this.valueDouble = value;
            this.stringValue = null;
        }

        public Entry(EntryType type, String value) {
            this.type = type;
            this.nameIndex = 0;
            this.descriptorIndex = 0;
            this.classIndex = 0;
            this.valueInt = 0;
            this.valueLong = 0;
            this.valueFloat = 0;
            this.valueDouble = 0;
            this.stringValue = value;
        }

        public int getInt() { return valueInt; }
        public long getLong() { return valueLong; }
        public float getFloat() { return valueFloat; }
        public double getDouble() { return valueDouble; }
        public String getString() { return stringValue; }
    }

    public enum EntryType {
        UTF8,
        CLASS,
        METHODREF,
        FIELDREF,
        STRING,
        INTEGER,
        FLOAT,
        LONG,
        DOUBLE,
        NAMEANDTYPE
    }
}
