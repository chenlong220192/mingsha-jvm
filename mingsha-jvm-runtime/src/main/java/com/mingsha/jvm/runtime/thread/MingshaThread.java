package com.mingsha.jvm.runtime.thread;

import com.mingsha.jvm.runtime.stack.JavaStack;

/**
 * Represents a thread in the JVM.
 */
public class MingshaThread {
    public enum ThreadState { NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED }

    private final long id;
    private final String name;
    private final int priority;
    private final JavaStack stack;
    private ThreadState state;
    private boolean daemon;
    private int currentPc;

    private static long threadCounter = 0;

    public MingshaThread() {
        this("Thread-" + (++threadCounter), Thread.NORM_PRIORITY);
    }

    public MingshaThread(String name, int priority) {
        this.id = ++threadCounter;
        this.name = name;
        this.priority = priority;
        this.stack = new JavaStack();
        this.state = ThreadState.NEW;
        this.daemon = false;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public int getPriority() { return priority; }
    public JavaStack getStack() { return stack; }
    public ThreadState getState() { return state; }
    public void setState(ThreadState state) { this.state = state; }
    public boolean isDaemon() { return daemon; }
    public void setDaemon(boolean daemon) { this.daemon = daemon; }
    public int getCurrentPc() { return currentPc; }
    public void setCurrentPc(int pc) { this.currentPc = pc; }
    public boolean isAlive() { return state != ThreadState.NEW && state != ThreadState.TERMINATED; }
}
