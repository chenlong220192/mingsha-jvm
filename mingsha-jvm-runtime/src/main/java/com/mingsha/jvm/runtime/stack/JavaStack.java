package com.mingsha.jvm.runtime.stack;

import java.util.EmptyStackException;

/**
 * Represents the Java stack for a thread.
 */
public class JavaStack {
    private final int maxSize;
    private final StackFrame[] frames;
    private int top;
    private int size;

    public JavaStack() { this(1048576); }

    public JavaStack(int maxSize) {
        this.maxSize = maxSize;
        this.frames = new StackFrame[maxSize / 256];
        this.top = -1;
        this.size = 0;
    }

    public void pushFrame(StackFrame frame) {
        if (top >= frames.length - 1) throw new StackOverflowError("Java stack overflow");
        frames[++top] = frame;
        size += frame.getMaxLocals() + frame.getMaxStack();
    }

    public StackFrame popFrame() {
        if (top < 0) throw new EmptyStackException();
        return frames[top--];
    }

    public StackFrame getTopFrame() { return top >= 0 ? frames[top] : null; }
    public boolean isEmpty() { return top < 0; }
    public int getFrameCount() { return top + 1; }
}
