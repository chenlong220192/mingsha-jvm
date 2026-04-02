package com.mingsha.jvm.runtime.stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mingsha.jvm.core.classfile.ConstantPool;
import com.mingsha.jvm.runtime.methodarea.KlassModel;

/**
 * Represents a JVM stack frame.
 * <p>
 * A stack frame contains:
 * <ul>
 *   <li>Local variables table</li>
 *   <li>Operand stack</li>
 *   <li>Reference to constant pool</li>
 *   <li>Return address</li>
 * </ul>
 *
 * @version 1.0.0
 */
public class StackFrame {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(StackFrame.class);

    /** Maximum number of local variables */
    private final int maxLocals;

    /** Maximum stack depth */
    private final int maxStack;

    /** Local variables array */
    private final Object[] localVariables;

    /** Operand stack array */
    private final Object[] operandStack;

    /** Current stack top position */
    private int operandStackTop;

    /** Current program counter */
    private int currentPc;

    /** Method name for debugging */
    private String methodName;

    /** Class name for debugging */
    private String className;

    /** Link to previous frame (caller) */
    private StackFrame nextFrame;

    /** Constant pool reference for method resolution */
    private ConstantPool constantPool;

    /** Current class for method resolution */
    private KlassModel currentKlass;

    /** Return value for method return */
    private Object returnValue;

    /**
     * Constructor with sizes.
     *
     * @param maxLocals maximum local variable count
     * @param maxStack maximum operand stack depth
     */
    public StackFrame(int maxLocals, int maxStack) {
        this.maxLocals = maxLocals;
        this.maxStack = maxStack;
        this.localVariables = new Object[maxLocals];
        this.operandStack = new Object[maxStack];
        this.operandStackTop = 0;
        logger.trace("Created StackFrame: maxLocals={}, maxStack={}", maxLocals, maxStack);
    }

    /** @return maximum local variables */
    public int getMaxLocals() { return maxLocals; }

    /** @return maximum stack depth */
    public int getMaxStack() { return maxStack; }

    /** @return current program counter */
    public int getCurrentPc() { return currentPc; }

    /** @param pc new program counter */
    public void setCurrentPc(int pc) { this.currentPc = pc; }

    /** @param name method name */
    public void setMethodName(String name) { this.methodName = name; }

    /** @return method name */
    public String getMethodName() { return methodName; }

    /** @param name class name */
    public void setClassName(String name) { this.className = name; }

    /** @return class name */
    public String getClassName() { return className; }

    /** @return next frame (caller) */
    public StackFrame getNextFrame() { return nextFrame; }

    /** @param frame next frame */
    public void setNextFrame(StackFrame frame) { this.nextFrame = frame; }

    /** @return constant pool */
    public ConstantPool getConstantPool() { return constantPool; }

    /** @param cp constant pool */
    public void setConstantPool(ConstantPool cp) { this.constantPool = cp; }

    /** @return current klass */
    public KlassModel getCurrentKlass() { return currentKlass; }

    /** @param klass current klass */
    public void setCurrentKlass(KlassModel klass) { this.currentKlass = klass; }

    /** @return return value */
    public Object getReturnValue() { return returnValue; }

    /** @param value return value */
    public void setReturnValue(Object value) { this.returnValue = value; }

    /**
     * Pushes value onto operand stack.
     *
     * @param value value to push
     * @throws StackOverflowError if stack is full
     */
    public void push(Object value) {
        if (operandStackTop >= maxStack) {
            throw new StackOverflowError("Operand stack overflow in " + methodName);
        }
        operandStack[operandStackTop++] = value;
        logger.trace("Push: {} (stack depth={})", value, operandStackTop);
    }

    /**
     * Pops value from operand stack.
     *
     * @return popped value
     * @throws StackOverflowError if stack is empty
     */
    public Object pop() {
        if (operandStackTop <= 0) {
            throw new StackOverflowError("Operand stack underflow in " + methodName);
        }
        logger.trace("Pop: {} (stack depth={})", operandStack[operandStackTop - 1], operandStackTop - 1);
        return operandStack[--operandStackTop];
    }

    /** @return top of stack without popping */
    public Object peek() {
        return operandStack[operandStackTop - 1];
    }

    /**
     * Pops int from operand stack.
     * @return int value
     */
    public int popInt() { return ((Integer) pop()).intValue(); }

    /**
     * Pushes int onto operand stack.
     * @param value int value
     */
    public void pushInt(int value) { push(Integer.valueOf(value)); }

    /**
     * Pops long from operand stack (uses two slots).
     * @return long value
     */
    public long popLong() { return ((Long) pop()).longValue(); }

    /**
     * Pushes long onto operand stack (uses two slots).
     * @param value long value
     */
    public void pushLong(long value) { push(Long.valueOf(value)); }

    /**
     * Gets local variable at index.
     *
     * @param index variable index
     * @return variable value
     */
    public Object getLocalVariable(int index) { return localVariables[index]; }

    /**
     * Sets local variable at index.
     *
     * @param index variable index
     * @param value new value
     */
    public void setLocalVariable(int index, Object value) {
        localVariables[index] = value;
        logger.trace("Set local[{}] = {}", index, value);
    }

    /**
     * Gets int local variable.
     *
     * @param index variable index
     * @return int value
     */
    public int getLocalVariableInt(int index) {
        return ((Integer) localVariables[index]).intValue();
    }

    /**
     * Sets int local variable.
     *
     * @param index variable index
     * @param value int value
     */
    public void setLocalVariableInt(int index, int value) {
        localVariables[index] = Integer.valueOf(value);
        logger.trace("Set local[{}] = {}", index, value);
    }

    /** @return current stack depth */
    public int getStackDepth() { return operandStackTop; }

    @Override
    public String toString() {
        return String.format("StackFrame{method=%s.%s, pc=%d, stack=%d/%d}",
                className, methodName, currentPc, operandStackTop, maxStack);
    }
}
