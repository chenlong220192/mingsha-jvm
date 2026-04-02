package com.mingsha.jvm.runtime.heap;

import com.mingsha.jvm.runtime.methodarea.KlassModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class HeapObject {

    private static final Logger logger = LoggerFactory.getLogger(HeapObject.class);

    private final KlassModel klass;
    private final Object[] instanceFields;
    private final long objectId;
    private static long idCounter = 0;

    public HeapObject(KlassModel klass) {
        this.klass = klass;
        this.instanceFields = new Object[countInstanceFields(klass)];
        this.objectId = nextId();
        logger.trace("Created HeapObject: id={}, class={}", objectId, klass.getName());
    }

    private static synchronized long nextId() {
        return ++idCounter;
    }

    private static int countInstanceFields(KlassModel klass) {
        int count = 0;
        if (klass.getSuperClassName() != null && !klass.getSuperClassName().isEmpty()) {
            count += 2;
        }
        count += klass.getFields().size();
        return Math.max(count, 4);
    }

    public KlassModel getKlass() {
        return klass;
    }

    public long getObjectId() {
        return objectId;
    }

    public Object getField(int index) {
        if (index >= 0 && index < instanceFields.length) {
            return instanceFields[index];
        }
        return null;
    }

    public void setField(int index, Object value) {
        if (index >= 0 && index < instanceFields.length) {
            instanceFields[index] = value;
            logger.trace("HeapObject.setField: id={}, index={}, value={}", objectId, index, value);
        }
    }

    public Object getField(String name) {
        Integer index = klass.getFieldIndex(name);
        if (index != null) {
            return getField(index);
        }
        return null;
    }

    public void setField(String name, Object value) {
        Integer index = klass.getFieldIndex(name);
        if (index != null) {
            setField(index, value);
        }
    }

    public int getIntField(int index) {
        Object value = getField(index);
        if (value instanceof Integer) {
            return (Integer) value;
        }
        return 0;
    }

    public void setIntField(int index, int value) {
        setField(index, value);
    }

    public long getLongField(int index) {
        Object value = getField(index);
        if (value instanceof Long) {
            return (Long) value;
        }
        return 0L;
    }

    public void setLongField(int index, long value) {
        setField(index, value);
    }

    public Object getObjectField(int index) {
        return getField(index);
    }

    public void setObjectField(int index, Object value) {
        setField(index, value);
    }

    @Override
    public String toString() {
        return "HeapObject@" + Long.toHexString(objectId) + "[" + klass.getName() + "]";
    }
}
