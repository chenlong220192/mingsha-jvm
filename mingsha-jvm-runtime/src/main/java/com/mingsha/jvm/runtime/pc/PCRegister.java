package com.mingsha.jvm.runtime.pc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Program Counter register for JVM thread.
 * <p>
 * Stores the address of the currently executing bytecode instruction.
 * Each thread has its own PC register.
 *
 * @version 1.0.0
 */
public class PCRegister {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(PCRegister.class);

    private int pc;
    private String currentMethod;
    private int bytecodeOffset;

    /** @return current PC value */
    public int getPc() { return pc; }

    /** @param pc new PC value */
    public void setPc(int pc) {
        this.pc = pc;
        logger.trace("PC set to {}", pc);
    }

    /** @param offset PC offset */
    public void incrementPc(int offset) { this.pc += offset; }

    /** @return current method name */
    public String getCurrentMethod() { return currentMethod; }

    /** @param method current method name */
    public void setCurrentMethod(String method) {
        this.currentMethod = method;
        logger.trace("Current method: {}", method);
    }

    /** @return bytecode offset */
    public int getBytecodeOffset() { return bytecodeOffset; }

    /** @param offset bytecode offset */
    public void setBytecodeOffset(int offset) { this.bytecodeOffset = offset; }

    /** Resets PC register */
    public void reset() {
        this.pc = 0;
        this.currentMethod = null;
        this.bytecodeOffset = 0;
        logger.trace("PCRegister reset");
    }
}
