package com.mingsha.jvm.runtime.methodarea;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KlassModelTest {

    @Test
    void testKlassModelCreation() {
        KlassModel klass = new KlassModel("java/lang/Object", "", 1);
        assertNotNull(klass);
        assertEquals("java/lang/Object", klass.getName());
    }

    @Test
    void testAddMethod() {
        KlassModel klass = new KlassModel("TestClass", "", 1);
        KlassModel.MethodInfo method = new KlassModel.MethodInfo("main", "([Ljava/lang/String;)V", 1);
        klass.addMethod(method);
        assertEquals(1, klass.getMethods().size());
    }

    @Test
    void testAddField() {
        KlassModel klass = new KlassModel("TestClass", "", 1);
        KlassModel.FieldInfo field = new KlassModel.FieldInfo("count", "I", 1);
        klass.addField(field);
        assertEquals(1, klass.getFields().size());
    }

    @Test
    void testSuperClassName() {
        KlassModel klass = new KlassModel("TestClass", "java/lang/Object", 1);
        assertEquals("java/lang/Object", klass.getSuperClassName());
    }

    @Test
    void testAccessFlags() {
        KlassModel klass = new KlassModel("TestClass", "", 0x0001);
        assertEquals(0x0001, klass.getAccessFlags());
    }

    @Test
    void testMethodInfo() {
        KlassModel.MethodInfo method = new KlassModel.MethodInfo("test", "()V", 1);
        assertEquals("test", method.name);
        assertEquals("()V", method.descriptor);
        assertEquals(1, method.accessFlags);
        method.maxStack = 5;
        method.maxLocals = 2;
        assertEquals(5, method.maxStack);
        assertEquals(2, method.maxLocals);
    }

    @Test
    void testFieldInfo() {
        KlassModel.FieldInfo field = new KlassModel.FieldInfo("value", "I", 1);
        assertEquals("value", field.name);
        assertEquals("I", field.descriptor);
        assertEquals(1, field.accessFlags);
    }
}