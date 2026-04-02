package com.mingsha.jvm.interpreter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mingsha.jvm.core.MingshaVMProperties;
import com.mingsha.jvm.core.constants.JVMConstants;
import com.mingsha.jvm.core.utils.BytecodeReader;
import com.mingsha.jvm.runtime.stack.JavaStack;
import com.mingsha.jvm.runtime.stack.StackFrame;
import com.mingsha.jvm.runtime.thread.MingshaThread;
import com.mingsha.jvm.runtime.methodarea.KlassModel;

/**
 * Loop-based bytecode interpreter for Mingsha JVM.
 * <p>
 * Implements the fetch-decode-execute cycle for JVM bytecode.
 *
 * @version 1.0.0
 */
public class LoopInterpreter {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(LoopInterpreter.class);

    /** Singleton instance */
    private static final LoopInterpreter INSTANCE = new LoopInterpreter();

    /** Hot spot threshold for JIT compilation */
    private final int hotSpotThreshold;

    /** Private constructor for singleton */
    private LoopInterpreter() {
        this.hotSpotThreshold = Integer.parseInt(
                MingshaVMProperties.getInstance().getProperty("interpreter.hotSpotThreshold", "1000"));
        logger.info("LoopInterpreter initialized with hotSpotThreshold={}", hotSpotThreshold);
    }

    /** @return singleton instance */
    public static LoopInterpreter getInstance() { return INSTANCE; }

    /** @return hot spot threshold */
    public int getHotSpotThreshold() { return hotSpotThreshold; }

    /**
     * Interprets bytecode.
     *
     * @param thread the executing thread
     * @param klass the class
     * @param method the method
     * @param bytecode the method bytecode
     */
    public void interpret(MingshaThread thread, KlassModel klass, KlassModel.MethodInfo method, byte[] bytecode) {
        logger.debug("Interpreting method: {} in class {}", method.name, klass.getName());

        StackFrame frame = thread.getStack().getTopFrame();
        if (frame == null) {
            frame = new StackFrame(method.maxLocals, method.maxStack);
            frame.setMethodName(method.name);
            frame.setClassName(klass.getName());
            thread.getStack().pushFrame(frame);
        }

        BytecodeReader reader = new BytecodeReader(bytecode);
        int instructionCount = 0;

        try {
            while (reader.hasRemaining()) {
                int opcode = reader.readUnsignedByte();
                int pc = reader.getPosition() - 1;
                frame.setCurrentPc(pc);

                logger.trace("Executing opcode: 0x{} at pc={}", Integer.toHexString(opcode), pc);

                executeInstruction(opcode, frame, reader);
                instructionCount++;

                if (instructionCount % 10000 == 0) {
                    logger.debug("Executed {} instructions in {}", instructionCount, method.name);
                }
            }
        } catch (ReturnException e) {
            logger.debug("Method {} returned: {}", method.name, e.returnValue);
        } catch (Exception e) {
            logger.error("Exception in {} at pc={}: {}", method.name, frame.getCurrentPc(), e.getMessage(), e);
            throw e;
        }

        logger.debug("Method {} completed after {} instructions", method.name, instructionCount);
    }

    /**
     * Executes a bytecode instruction.
     *
     * @param opcode the bytecode opcode
     * @param frame the stack frame
     * @param reader the bytecode reader
     */
    private void executeInstruction(int opcode, StackFrame frame, BytecodeReader reader) {
        switch (opcode) {
            // Constants
            case JVMConstants.NOP -> logger.trace("NOP");
            case JVMConstants.ACONST_NULL -> frame.push(null);
            case JVMConstants.ICONST_M1 -> frame.pushInt(-1);
            case JVMConstants.ICONST_0 -> frame.pushInt(0);
            case JVMConstants.ICONST_1 -> frame.pushInt(1);
            case JVMConstants.ICONST_2 -> frame.pushInt(2);
            case JVMConstants.ICONST_3 -> frame.pushInt(3);
            case JVMConstants.ICONST_4 -> frame.pushInt(4);
            case JVMConstants.ICONST_5 -> frame.pushInt(5);
            case JVMConstants.LCONST_0 -> frame.pushLong(0L);
            case JVMConstants.LCONST_1 -> frame.pushLong(1L);
            case JVMConstants.FCONST_0 -> frame.push(Float.valueOf(0f));
            case JVMConstants.FCONST_1 -> frame.push(Float.valueOf(1f));
            case JVMConstants.FCONST_2 -> frame.push(Float.valueOf(2f));
            case JVMConstants.DCONST_0 -> frame.push(Double.valueOf(0.0));
            case JVMConstants.DCONST_1 -> frame.push(Double.valueOf(1.0));

            // Push
            case JVMConstants.BIPUSH -> frame.pushInt(reader.readByte());
            case JVMConstants.SIPUSH -> frame.pushInt(reader.readShort());
            case JVMConstants.LDC -> frame.push("ldc_" + reader.readUnsignedByte());

            // Load
            case JVMConstants.ILOAD -> frame.pushInt(frame.getLocalVariableInt(reader.readUnsignedByte()));
            case JVMConstants.LLOAD -> frame.pushLong(frame.popLong());
            case JVMConstants.FLOAD -> frame.push(frame.getLocalVariable(reader.readUnsignedByte()));
            case JVMConstants.DLOAD -> frame.push(frame.getLocalVariable(reader.readUnsignedByte()));
            case JVMConstants.ALOAD -> frame.push(frame.getLocalVariable(reader.readUnsignedByte()));

            // Store
            case JVMConstants.ISTORE -> frame.setLocalVariableInt(reader.readUnsignedByte(), frame.popInt());
            case JVMConstants.LSTORE -> frame.setLocalVariable(reader.readUnsignedByte(), frame.popLong());
            case JVMConstants.FSTORE -> frame.setLocalVariable(reader.readUnsignedByte(), frame.pop());
            case JVMConstants.DSTORE -> frame.setLocalVariable(reader.readUnsignedByte(), frame.pop());
            case JVMConstants.ASTORE -> frame.setLocalVariable(reader.readUnsignedByte(), frame.pop());

            // Stack
            case JVMConstants.POP -> frame.pop();
            case JVMConstants.POP2 -> { frame.pop(); frame.pop(); }
            case JVMConstants.DUP -> { Object v = frame.pop(); frame.push(v); frame.push(v); }
            case JVMConstants.SWAP -> { Object v1 = frame.pop(); Object v2 = frame.pop(); frame.push(v1); frame.push(v2); }

            // Arithmetic
            case JVMConstants.IADD -> { int v2 = frame.popInt(); int v1 = frame.popInt(); frame.pushInt(v1 + v2); }
            case JVMConstants.LADD -> { long v2 = frame.popLong(); long v1 = frame.popLong(); frame.pushLong(v1 + v2); }
            case JVMConstants.ISUB -> { int v2 = frame.popInt(); int v1 = frame.popInt(); frame.pushInt(v1 - v2); }
            case JVMConstants.IMUL -> { int v2 = frame.popInt(); int v1 = frame.popInt(); frame.pushInt(v1 * v2); }
            case JVMConstants.IDIV -> {
                int v2 = frame.popInt();
                if (v2 == 0) throw new ArithmeticException("division by zero");
                frame.pushInt(frame.popInt() / v2);
            }
            case JVMConstants.IREM -> {
                int v2 = frame.popInt();
                if (v2 == 0) throw new ArithmeticException("modulo by zero");
                frame.pushInt(frame.popInt() % v2);
            }
            case JVMConstants.IINC -> {
                int idx = reader.readUnsignedByte();
                int c = reader.readByte();
                frame.setLocalVariableInt(idx, frame.getLocalVariableInt(idx) + c);
            }

            // Comparison
            case JVMConstants.IFEQ -> { int offset = reader.readShort(); if (frame.popInt() == 0) reader.skip(offset - 3); }
            case JVMConstants.IFNE -> { int offset = reader.readShort(); if (frame.popInt() != 0) reader.skip(offset - 3); }
            case JVMConstants.IFLT -> { int offset = reader.readShort(); if (frame.popInt() < 0) reader.skip(offset - 3); }
            case JVMConstants.IFGE -> { int offset = reader.readShort(); if (frame.popInt() >= 0) reader.skip(offset - 3); }
            case JVMConstants.IFGT -> { int offset = reader.readShort(); if (frame.popInt() > 0) reader.skip(offset - 3); }
            case JVMConstants.IFLE -> { int offset = reader.readShort(); if (frame.popInt() <= 0) reader.skip(offset - 3); }

            // Control
            case JVMConstants.GOTO -> reader.skip(reader.readShort() - 3);
            case JVMConstants.RETURN -> throw new ReturnException(null);
            case JVMConstants.IRETURN -> throw new ReturnException(frame.popInt());
            case JVMConstants.LRETURN -> throw new ReturnException(frame.popLong());
            case JVMConstants.FRETURN -> throw new ReturnException(frame.pop());
            case JVMConstants.DRETURN -> throw new ReturnException(frame.pop());
            case JVMConstants.ARETURN -> throw new ReturnException(frame.pop());

            // Method invocation
            case JVMConstants.INVOKEVIRTUAL, JVMConstants.INVOKESPECIAL, JVMConstants.INVOKESTATIC -> reader.readUnsignedShort();
            case JVMConstants.INVOKEINTERFACE -> { reader.readUnsignedShort(); reader.readUnsignedByte(); reader.readUnsignedByte(); }

            // Object creation
            case JVMConstants.NEW -> frame.push("new_obj_" + reader.readUnsignedShort());
            case JVMConstants.NEWARRAY -> frame.push("array_" + reader.readUnsignedByte() + "_" + frame.popInt());
            case JVMConstants.ARRAYLENGTH -> frame.pushInt(0);

            // Field access
            case JVMConstants.GETSTATIC -> frame.push("static_" + reader.readUnsignedShort());
            case JVMConstants.PUTSTATIC -> frame.pop();
            case JVMConstants.GETFIELD -> { frame.pop(); frame.push("field_" + reader.readUnsignedShort()); }
            case JVMConstants.PUTFIELD -> { frame.pop(); frame.pop(); }

            default -> logger.trace("Unhandled opcode: 0x{}", Integer.toHexString(opcode));
        }
    }

    /**
     * Exception thrown when return instruction is executed.
     */
    public static class ReturnException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public final Object returnValue;
        public ReturnException(Object returnValue) { this.returnValue = returnValue; }
    }
}
