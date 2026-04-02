package com.mingsha.jvm.runtime.stack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StackFrameTest {

    @Test
    void testStackFrameCreation() {
        StackFrame frame = new StackFrame(10, 20);
        assertNotNull(frame);
        assertEquals(10, frame.getMaxLocals());
        assertEquals(20, frame.getMaxStack());
    }

    @Test
    void testPushAndPop() {
        StackFrame frame = new StackFrame(10, 20);
        frame.push("value");
        assertEquals("value", frame.pop());
    }

    @Test
    void testPeek() {
        StackFrame frame = new StackFrame(10, 20);
        frame.push("value");
        assertEquals("value", frame.peek());
        assertEquals(1, frame.getStackDepth());
    }

    @Test
    void testPushIntAndPopInt() {
        StackFrame frame = new StackFrame(10, 20);
        frame.pushInt(42);
        assertEquals(42, frame.popInt());
    }

    @Test
    void testPushLongAndPopLong() {
        StackFrame frame = new StackFrame(10, 20);
        frame.pushLong(123456789L);
        assertEquals(123456789L, frame.popLong());
    }

    @Test
    void testLocalVariables() {
        StackFrame frame = new StackFrame(10, 20);
        frame.setLocalVariable(0, "test");
        assertEquals("test", frame.getLocalVariable(0));
    }

    @Test
    void testLocalVariablesInt() {
        StackFrame frame = new StackFrame(10, 20);
        frame.setLocalVariableInt(1, 100);
        assertEquals(100, frame.getLocalVariableInt(1));
    }

    @Test
    void testStackOverflow() {
        StackFrame frame = new StackFrame(10, 2);
        frame.pushInt(1);
        frame.pushInt(2);
        assertThrows(StackOverflowError.class, () -> frame.pushInt(3));
    }

    @Test
    void testStackUnderflow() {
        StackFrame frame = new StackFrame(10, 20);
        assertThrows(StackOverflowError.class, () -> frame.pop());
    }

    @Test
    void testMethodAndClassName() {
        StackFrame frame = new StackFrame(10, 20);
        frame.setMethodName("main");
        frame.setClassName("Test");
        assertEquals("main", frame.getMethodName());
        assertEquals("Test", frame.getClassName());
    }

    @Test
    void testCurrentPc() {
        StackFrame frame = new StackFrame(10, 20);
        frame.setCurrentPc(100);
        assertEquals(100, frame.getCurrentPc());
    }

    @Test
    void testNextFrame() {
        StackFrame frame1 = new StackFrame(10, 20);
        StackFrame frame2 = new StackFrame(10, 20);
        frame1.setNextFrame(frame2);
        assertEquals(frame2, frame1.getNextFrame());
    }

    @Test
    void testStackDepth() {
        StackFrame frame = new StackFrame(10, 20);
        assertEquals(0, frame.getStackDepth());
        frame.pushInt(1);
        assertEquals(1, frame.getStackDepth());
        frame.pushInt(2);
        assertEquals(2, frame.getStackDepth());
    }

    @Test
    void testToString() {
        StackFrame frame = new StackFrame(10, 20);
        frame.setMethodName("main");
        frame.setClassName("Test");
        String str = frame.toString();
        assertTrue(str.contains("main"));
        assertTrue(str.contains("Test"));
    }
}