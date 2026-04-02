package com.mingsha.jvm.runtime.thread;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MingshaThreadTest {

    @Test
    void testThreadCreation() {
        MingshaThread thread = new MingshaThread("TestThread", Thread.NORM_PRIORITY);
        assertNotNull(thread);
        assertEquals("TestThread", thread.getName());
        assertEquals(Thread.NORM_PRIORITY, thread.getPriority());
    }

    @Test
    void testThreadId() {
        MingshaThread thread1 = new MingshaThread("Thread1", Thread.NORM_PRIORITY);
        MingshaThread thread2 = new MingshaThread("Thread2", Thread.NORM_PRIORITY);
        assertTrue(thread2.getId() > thread1.getId());
    }

    @Test
    void testThreadState() {
        MingshaThread thread = new MingshaThread("TestThread", Thread.NORM_PRIORITY);
        assertEquals(MingshaThread.ThreadState.NEW, thread.getState());
        thread.setState(MingshaThread.ThreadState.RUNNABLE);
        assertEquals(MingshaThread.ThreadState.RUNNABLE, thread.getState());
    }

    @Test
    void testDaemonFlag() {
        MingshaThread thread = new MingshaThread("TestThread", Thread.NORM_PRIORITY);
        assertFalse(thread.isDaemon());
        thread.setDaemon(true);
        assertTrue(thread.isDaemon());
    }

    @Test
    void testCurrentPc() {
        MingshaThread thread = new MingshaThread("TestThread", Thread.NORM_PRIORITY);
        assertEquals(0, thread.getCurrentPc());
        thread.setCurrentPc(100);
        assertEquals(100, thread.getCurrentPc());
    }

    @Test
    void testIsAlive() {
        MingshaThread thread = new MingshaThread("TestThread", Thread.NORM_PRIORITY);
        assertFalse(thread.isAlive());
        thread.setState(MingshaThread.ThreadState.RUNNABLE);
        assertTrue(thread.isAlive());
        thread.setState(MingshaThread.ThreadState.TERMINATED);
        assertFalse(thread.isAlive());
    }

    @Test
    void testGetStack() {
        MingshaThread thread = new MingshaThread("TestThread", Thread.NORM_PRIORITY);
        assertNotNull(thread.getStack());
    }
}