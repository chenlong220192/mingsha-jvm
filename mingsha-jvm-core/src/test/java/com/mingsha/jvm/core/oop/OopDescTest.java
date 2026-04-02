package com.mingsha.jvm.core.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.mingsha.jvm.core.oop.*;

/**
 * Unit tests for OopDesc and InstanceOop.
 */
class OopDescTest {

    @Test
    void testOopDescCreation() {
        OopDesc oop = new OopDesc();
        assertNotNull(oop);
        assertEquals(0, oop.getMarkWord());
        assertEquals(0, oop.getKlassPointer());
    }

    @Test
    void testOopDescSize() {
        OopDesc oop = new OopDesc();
        assertTrue(oop.size() > 0);
    }

    @Test
    void testInstanceOopCreation() {
        InstanceOop oop = new InstanceOop(5);
        assertNotNull(oop);
        assertEquals(5, oop.getFieldCount());
    }

    @Test
    void testInstanceOopFieldAccess() {
        InstanceOop oop = new InstanceOop(2);
        oop.setField(0, "test");
        assertEquals("test", oop.getField(0));
    }

    @Test
    void testInstanceOopSize() {
        InstanceOop oop = new InstanceOop(10);
        assertTrue(oop.size() > 16);
    }

    @Test
    void testArrayOop() {
        TypeArrayOop array = new TypeArrayOop(TypeArrayOop.ElementType.T_INT, 100);
        assertNotNull(array);
        assertEquals(100, array.getLength());
    }

    @Test
    void testObjArrayOop() {
        ObjArrayOop array = new ObjArrayOop(50);
        assertNotNull(array);
        assertEquals(50, array.getLength());
    }
}
