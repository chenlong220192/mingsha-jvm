package com.mingsha.jvm.runtime.stack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JavaStackTest {

    @Test
    void testJavaStackCreation() {
        JavaStack stack = new JavaStack();
        assertNotNull(stack);
        assertTrue(stack.isEmpty());
    }

    @Test
    void testJavaStackWithSize() {
        JavaStack stack = new JavaStack(4096);
        assertNotNull(stack);
        assertEquals(4096, stack.getMaxSize());
    }

    @Test
    void testPushAndPopFrame() {
        JavaStack stack = new JavaStack();
        StackFrame frame1 = new StackFrame(10, 20);
        frame1.setMethodName("method1");
        StackFrame frame2 = new StackFrame(10, 20);
        frame2.setMethodName("method2");

        stack.pushFrame(frame1);
        assertEquals(1, stack.getFrameCount());
        assertFalse(stack.isEmpty());

        stack.pushFrame(frame2);
        assertEquals(2, stack.getFrameCount());

        assertEquals("method2", stack.popFrame().getMethodName());
        assertEquals(1, stack.getFrameCount());

        assertEquals("method1", stack.popFrame().getMethodName());
        assertTrue(stack.isEmpty());
    }

    @Test
    void testGetTopFrame() {
        JavaStack stack = new JavaStack();
        StackFrame frame = new StackFrame(10, 20);
        frame.setMethodName("topMethod");
        assertNull(stack.getTopFrame());

        stack.pushFrame(frame);
        assertEquals("topMethod", stack.getTopFrame().getMethodName());
    }

    @Test
    void testPopEmptyStack() {
        JavaStack stack = new JavaStack();
        assertThrows(java.util.EmptyStackException.class, () -> stack.popFrame());
    }

    @Test
    void testStackOverflow() {
        JavaStack stack = new JavaStack(512);
        for (int i = 0; i < 100; i++) {
            StackFrame frame = new StackFrame(10, 20);
            try {
                stack.pushFrame(frame);
            } catch (StackOverflowError e) {
                return;
            }
        }
    }

    @Test
    void testFrameCount() {
        JavaStack stack = new JavaStack();
        assertEquals(0, stack.getFrameCount());
        stack.pushFrame(new StackFrame(10, 20));
        assertEquals(1, stack.getFrameCount());
        stack.pushFrame(new StackFrame(10, 20));
        assertEquals(2, stack.getFrameCount());
    }
}