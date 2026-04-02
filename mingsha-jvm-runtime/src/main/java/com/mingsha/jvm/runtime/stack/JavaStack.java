package com.mingsha.jvm.runtime.stack;

import java.util.EmptyStackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the Java stack for a thread.
 * <p>
 * Each thread has its own stack, which holds stack frames.
 *
 * @version 1.0.0
 */
public class JavaStack {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(JavaStack.class);

    /** Maximum stack size in bytes */
    private final int maxSize;

    /** Array of stack frames */
    private final StackFrame[] frames;

    /** Current top of stack (-1 if empty) */
    private int top;

    /** Total size used in bytes */
    private int size;

    /** Default stack size: 1MB */
    public JavaStack() { this(1048576); }

    /**
     * Constructor with stack size.
     *
     * @param maxSize maximum stack size in bytes
     */
    public JavaStack(int maxSize) {
        this.maxSize = maxSize;
        this.frames = new StackFrame[maxSize / 256]; // Estimate frame count
        this.top = -1;
        this.size = 0;
        logger.debug("JavaStack created: maxSize={} bytes", maxSize);
    }

    /**
     * Pushes a frame onto the stack.
     *
     * @param frame the frame to push
     * @throws StackOverflowError if stack is full
     */
    public void pushFrame(StackFrame frame) {
        if (top >= frames.length - 1) {
            logger.error("Stack overflow: size={}", size);
            throw new StackOverflowError("Java stack overflow");
        }
        frames[++top] = frame;
        size += frame.getMaxLocals() + frame.getMaxStack();
        logger.trace("Push frame: {} (stack size={})", frame.getMethodName(), size);
    }

    /**
     * Pops a frame from the stack.
     *
     * @return the popped frame
     * @throws EmptyStackException if stack is empty
     */
    public StackFrame popFrame() {
        if (top < 0) {
            throw new EmptyStackException();
        }
        StackFrame frame = frames[top--];
        size -= frame.getMaxLocals() + frame.getMaxStack();
        logger.trace("Pop frame: {} (stack size={})", frame.getMethodName(), size);
        return frame;
    }

    /** @return top frame or null if empty */
    public StackFrame getTopFrame() { return top >= 0 ? frames[top] : null; }

    /** @return true if stack is empty */
    public boolean isEmpty() { return top < 0; }

    /** @return number of frames on stack */
    public int getFrameCount() { return top + 1; }

    /** @return current stack size in bytes */
    public int getSize() { return size; }

    /** @return maximum stack size */
    public int getMaxSize() { return maxSize; }
}
