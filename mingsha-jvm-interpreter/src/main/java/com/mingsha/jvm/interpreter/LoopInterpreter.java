package com.mingsha.jvm.interpreter;

import com.mingsha.jvm.core.MingshaVMProperties;
import com.mingsha.jvm.core.constants.JVMConstants;
import com.mingsha.jvm.core.utils.BytecodeReader;
import com.mingsha.jvm.runtime.stack.JavaStack;
import com.mingsha.jvm.runtime.stack.StackFrame;
import com.mingsha.jvm.runtime.thread.MingshaThread;
import com.mingsha.jvm.runtime.methodarea.KlassModel;

/**
 * Loop-based bytecode interpreter.
 */
public class LoopInterpreter {
    private static final LoopInterpreter INSTANCE = new LoopInterpreter();
    private final int hotSpotThreshold;

    private LoopInterpreter() {
        this.hotSpotThreshold = Integer.parseInt(
                MingshaVMProperties.getInstance().getProperty("interpreter.hotSpotThreshold", "1000"));
    }

    public static LoopInterpreter getInstance() { return INSTANCE; }
    public int getHotSpotThreshold() { return hotSpotThreshold; }

    public void interpret(MingshaThread thread, KlassModel klass, KlassModel.MethodInfo method, byte[] bytecode) {
        StackFrame frame = thread.getStack().getTopFrame();
        if (frame == null) {
            frame = new StackFrame(method.maxLocals, method.maxStack);
            frame.setMethodName(method.name);
            thread.getStack().pushFrame(frame);
        }

        BytecodeReader reader = new BytecodeReader(bytecode);
        try {
            while (reader.hasRemaining()) {
                int opcode = reader.readByte() & 0xFF;
                frame.setCurrentPc(reader.getPosition() - 1);
                executeInstruction(opcode, frame, reader);
            }
        } catch (ReturnException e) {
            // Method completed
        }
    }

    private void executeInstruction(int opcode, StackFrame frame, BytecodeReader reader) {
        switch (opcode) {
            case JVMConstants.NOP -> {}
            case JVMConstants.ACONST_NULL -> frame.push(null);
            case JVMConstants.ICONST_M1 -> frame.pushInt(-1);
            case JVMConstants.ICONST_0 -> frame.pushInt(0);
            case JVMConstants.ICONST_1 -> frame.pushInt(1);
            case JVMConstants.ICONST_2 -> frame.pushInt(2);
            case JVMConstants.ICONST_3 -> frame.pushInt(3);
            case JVMConstants.ICONST_4 -> frame.pushInt(4);
            case JVMConstants.ICONST_5 -> frame.pushInt(5);
            case JVMConstants.ILOAD -> frame.pushInt(frame.getLocalVariableInt(reader.readUnsignedByte()));
            case JVMConstants.ISTORE -> frame.setLocalVariableInt(reader.readUnsignedByte(), frame.popInt());
            case JVMConstants.IADD -> { int v2 = frame.popInt(); int v1 = frame.popInt(); frame.pushInt(v1 + v2); }
            case JVMConstants.ISUB -> { int v2 = frame.popInt(); int v1 = frame.popInt(); frame.pushInt(v1 - v2); }
            case JVMConstants.IMUL -> { int v2 = frame.popInt(); int v1 = frame.popInt(); frame.pushInt(v1 * v2); }
            case JVMConstants.IDIV -> { int v2 = frame.popInt(); if (v2 == 0) throw new ArithmeticException(); frame.pushInt(frame.popInt() / v2); }
            case JVMConstants.IREM -> { int v2 = frame.popInt(); if (v2 == 0) throw new ArithmeticException(); frame.pushInt(frame.popInt() % v2); }
            case JVMConstants.IINC -> { int idx = reader.readUnsignedByte(); int c = reader.readByte(); frame.setLocalVariableInt(idx, frame.getLocalVariableInt(idx) + c); }
            case JVMConstants.IFEQ -> { int offset = reader.readShort(); if (frame.popInt() == 0) reader.skip(offset - 3); }
            case JVMConstants.IFNE -> { int offset = reader.readShort(); if (frame.popInt() != 0) reader.skip(offset - 3); }
            case JVMConstants.IFLT -> { int offset = reader.readShort(); if (frame.popInt() < 0) reader.skip(offset - 3); }
            case JVMConstants.IFGE -> { int offset = reader.readShort(); if (frame.popInt() >= 0) reader.skip(offset - 3); }
            case JVMConstants.IFGT -> { int offset = reader.readShort(); if (frame.popInt() > 0) reader.skip(offset - 3); }
            case JVMConstants.IFLE -> { int offset = reader.readShort(); if (frame.popInt() <= 0) reader.skip(offset - 3); }
            case JVMConstants.IF_ICMPEQ -> { int offset = reader.readShort(); int v2 = frame.popInt(); int v1 = frame.popInt(); if (v1 == v2) reader.skip(offset - 3); }
            case JVMConstants.IF_ICMPNE -> { int offset = reader.readShort(); int v2 = frame.popInt(); int v1 = frame.popInt(); if (v1 != v2) reader.skip(offset - 3); }
            case JVMConstants.IF_ICMPLT -> { int offset = reader.readShort(); int v2 = frame.popInt(); int v1 = frame.popInt(); if (v1 < v2) reader.skip(offset - 3); }
            case JVMConstants.IF_ICMPGE -> { int offset = reader.readShort(); int v2 = frame.popInt(); int v1 = frame.popInt(); if (v1 >= v2) reader.skip(offset - 3); }
            case JVMConstants.IF_ICMPGT -> { int offset = reader.readShort(); int v2 = frame.popInt(); int v1 = frame.popInt(); if (v1 > v2) reader.skip(offset - 3); }
            case JVMConstants.IF_ICMPLE -> { int offset = reader.readShort(); int v2 = frame.popInt(); int v1 = frame.popInt(); if (v1 <= v2) reader.skip(offset - 3); }
            case JVMConstants.GOTO -> reader.skip(reader.readShort() - 3);
            case JVMConstants.RETURN -> throw new ReturnException(null);
            case JVMConstants.IRETURN -> throw new ReturnException(frame.popInt());
            case JVMConstants.BIPUSH -> frame.pushInt(reader.readByte());
            case JVMConstants.SIPUSH -> frame.pushInt(reader.readShort());
            case JVMConstants.LDC -> frame.push("ldc_" + reader.readUnsignedByte());
            case JVMConstants.NEW -> frame.push("new_obj_" + reader.readUnsignedShort());
            case JVMConstants.GETSTATIC -> frame.push("static_" + reader.readUnsignedShort());
            case JVMConstants.PUTSTATIC -> frame.pop();
            case JVMConstants.INVOKEVIRTUAL, JVMConstants.INVOKESPECIAL, JVMConstants.INVOKESTATIC -> reader.readUnsignedShort();
            case JVMConstants.POP -> frame.pop();
            case JVMConstants.DUP -> { Object v = frame.pop(); frame.push(v); frame.push(v); }
            case JVMConstants.SWAP -> { Object v1 = frame.pop(); Object v2 = frame.pop(); frame.push(v1); frame.push(v2); }
            default -> { }
        }
    }

    public static class ReturnException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public final Object returnValue;
        public ReturnException(Object returnValue) { this.returnValue = returnValue; }
    }
}
