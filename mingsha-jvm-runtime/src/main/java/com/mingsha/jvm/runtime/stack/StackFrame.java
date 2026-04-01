package com.mingsha.jvm.runtime.stack;

/**
 * Represents a JVM stack frame.
 */
public class StackFrame {
    private final int maxLocals;
    private final int maxStack;
    private final Object[] localVariables;
    private final Object[] operandStack;
    private int operandStackTop;
    private int currentPc;
    private String methodName;
    private String className;
    private StackFrame nextFrame;

    public StackFrame(int maxLocals, int maxStack) {
        this.maxLocals = maxLocals;
        this.maxStack = maxStack;
        this.localVariables = new Object[maxLocals];
        this.operandStack = new Object[maxStack];
        this.operandStackTop = 0;
    }

    public int getMaxLocals() { return maxLocals; }
    public int getMaxStack() { return maxStack; }
    public int getCurrentPc() { return currentPc; }
    public void setCurrentPc(int pc) { this.currentPc = pc; }
    public void setMethodName(String name) { this.methodName = name; }
    public void setClassName(String name) { this.className = className; }
    public StackFrame getNextFrame() { return nextFrame; }
    public void setNextFrame(StackFrame frame) { this.nextFrame = frame; }

    public void push(Object value) { operandStack[operandStackTop++] = value; }
    public Object pop() { return operandStack[--operandStackTop]; }
    public Object peek() { return operandStack[operandStackTop - 1]; }

    public int popInt() { return ((Integer) pop()).intValue(); }
    public void pushInt(int value) { push(Integer.valueOf(value)); }

    public long popLong() { return ((Long) pop()).longValue(); }
    public void pushLong(long value) { push(Long.valueOf(value)); }

    public Object getLocalVariable(int index) { return localVariables[index]; }
    public void setLocalVariable(int index, Object value) { localVariables[index] = value; }
    public int getLocalVariableInt(int index) { return ((Integer) localVariables[index]).intValue(); }
    public void setLocalVariableInt(int index, int value) { localVariables[index] = Integer.valueOf(value); }
}
