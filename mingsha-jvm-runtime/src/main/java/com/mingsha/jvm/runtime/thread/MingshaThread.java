package com.mingsha.jvm.runtime.thread;

import com.mingsha.jvm.runtime.stack.JavaStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a thread in the JVM.
 *
 * @version 1.0.0
 */
public class MingshaThread {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(MingshaThread.class);

    /** Thread states */
    public enum ThreadState {
        NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED
    }

    private final long id;
    private final String name;
    private final int priority;
    private final JavaStack stack;
    private ThreadState state;
    private boolean daemon;
    private int currentPc;
    private static long threadCounter = 0;

    /** Default constructor */
    public MingshaThread() {
        this("Thread-" + (++threadCounter), Thread.NORM_PRIORITY);
    }

    /**
     * Constructor with name and priority.
     *
     * @param name thread name
     * @param priority thread priority
     */
    public MingshaThread(String name, int priority) {
        this.id = ++threadCounter;
        this.name = name;
        this.priority = priority;
        this.stack = new JavaStack();
        this.state = ThreadState.NEW;
        this.daemon = false;
        logger.debug("Thread created: {} (id={})", name, id);
    }

    /** @return thread ID */
    public long getId() { return id; }

    /** @return thread name */
    public String getName() { return name; }

    /** @return thread priority */
    public int getPriority() { return priority; }

    /** @return Java stack */
    public JavaStack getStack() { return stack; }

    /** @return current state */
    public ThreadState getState() { return state; }

    /** @param state new state */
    public void setState(ThreadState state) {
        this.state = state;
        logger.trace("Thread {} state: {}", name, state);
    }

    /** @return true if daemon thread */
    public boolean isDaemon() { return daemon; }

    /** @param daemon daemon flag */
    public void setDaemon(boolean daemon) { this.daemon = daemon; }

    /** @return current PC */
    public int getCurrentPc() { return currentPc; }

    /** @param pc new PC */
    public void setCurrentPc(int pc) { this.currentPc = pc; }

    /** @return true if thread is alive */
    public boolean isAlive() {
        return state != ThreadState.NEW && state != ThreadState.TERMINATED;
    }
}
