package com.mingsha.jvm.runtime.pc;

/**
 * Program Counter register for JVM thread.
 */
public class PCRegister {
    private int pc;
    private String currentMethod;
    private int bytecodeOffset;

    public int getPc() { return pc; }
    public void setPc(int pc) { this.pc = pc; }
    public void incrementPc(int offset) { this.pc += offset; }
    public String getCurrentMethod() { return currentMethod; }
    public void setCurrentMethod(String method) { this.currentMethod = method; }
    public int getBytecodeOffset() { return bytecodeOffset; }
    public void setBytecodeOffset(int offset) { this.bytecodeOffset = offset; }
    public void reset() { this.pc = 0; this.currentMethod = null; this.bytecodeOffset = 0; }
}
