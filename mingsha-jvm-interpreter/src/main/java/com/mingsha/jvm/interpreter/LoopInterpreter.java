package com.mingsha.jvm.interpreter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mingsha.jvm.core.MingshaVMProperties;
import com.mingsha.jvm.core.constants.JVMConstants;
import com.mingsha.jvm.core.utils.BytecodeReader;
import com.mingsha.jvm.core.classfile.ConstantPool;
import com.mingsha.jvm.runtime.heap.HeapObject;
import com.mingsha.jvm.runtime.heap.HeapSpace;
import com.mingsha.jvm.runtime.methodarea.MethodArea;
import com.mingsha.jvm.runtime.methodarea.KlassModel;
import com.mingsha.jvm.runtime.stack.JavaStack;
import com.mingsha.jvm.runtime.stack.StackFrame;
import com.mingsha.jvm.runtime.thread.MingshaThread;
import com.mingsha.jvm.runtime.MethodResolver;

public class LoopInterpreter {

    private static final Logger logger = LoggerFactory.getLogger(LoopInterpreter.class);

    private static final LoopInterpreter INSTANCE = new LoopInterpreter();

    private final int hotSpotThreshold;
    private final MethodResolver methodResolver;
    private final MethodArea methodArea;
    private final HeapSpace heapSpace;

    private LoopInterpreter() {
        this.hotSpotThreshold = Integer.parseInt(
                MingshaVMProperties.getInstance().getProperty("interpreter.hotSpotThreshold", "1000"));
        this.methodResolver = new MethodResolver();
        this.methodArea = MethodArea.getInstance();
        this.heapSpace = new HeapSpace(1024 * 1024, 64 * 1024 * 1024);
        logger.info("LoopInterpreter initialized with hotSpotThreshold={}", hotSpotThreshold);
    }

    public static LoopInterpreter getInstance() { return INSTANCE; }
    public int getHotSpotThreshold() { return hotSpotThreshold; }
    public MethodResolver getMethodResolver() { return methodResolver; }
    public MethodArea getMethodArea() { return methodArea; }
    public HeapSpace getHeapSpace() { return heapSpace; }

    public void interpret(MingshaThread thread, KlassModel klass, KlassModel.MethodInfo method, byte[] bytecode) {
        logger.debug("Interpreting method: {} in class {}", method.name, klass.getName());

        StackFrame frame = thread.getStack().getTopFrame();
        if (frame == null) {
            frame = new StackFrame(method.maxLocals, method.maxStack);
            frame.setMethodName(method.name);
            frame.setClassName(klass.getName());
            frame.setCurrentKlass(klass);
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

                executeInstruction(opcode, frame, reader, thread);
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

    private void executeInstruction(int opcode, StackFrame frame, BytecodeReader reader, MingshaThread thread) {
        switch (opcode) {
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

            case JVMConstants.BIPUSH -> frame.pushInt(reader.readByte());
            case JVMConstants.SIPUSH -> frame.pushInt(reader.readShort());
            case JVMConstants.LDC -> {
                int index = reader.readUnsignedByte();
                Object constValue = frame.getConstantPool() != null 
                    ? frame.getConstantPool().getConstant(index) 
                    : "ldc_" + index;
                frame.push(constValue);
            }

            case JVMConstants.ILOAD -> frame.pushInt(frame.getLocalVariableInt(reader.readUnsignedByte()));
            case JVMConstants.LLOAD -> frame.pushLong(frame.popLong());
            case JVMConstants.FLOAD -> frame.push(frame.getLocalVariable(reader.readUnsignedByte()));
            case JVMConstants.DLOAD -> frame.push(frame.getLocalVariable(reader.readUnsignedByte()));
            case JVMConstants.ALOAD -> frame.push(frame.getLocalVariable(reader.readUnsignedByte()));

            case JVMConstants.ISTORE -> frame.setLocalVariableInt(reader.readUnsignedByte(), frame.popInt());
            case JVMConstants.LSTORE -> frame.setLocalVariable(reader.readUnsignedByte(), frame.popLong());
            case JVMConstants.FSTORE -> frame.setLocalVariable(reader.readUnsignedByte(), frame.pop());
            case JVMConstants.DSTORE -> frame.setLocalVariable(reader.readUnsignedByte(), frame.pop());
            case JVMConstants.ASTORE -> frame.setLocalVariable(reader.readUnsignedByte(), frame.pop());

            case JVMConstants.POP -> frame.pop();
            case JVMConstants.POP2 -> { frame.pop(); frame.pop(); }
            case JVMConstants.DUP -> { Object v = frame.pop(); frame.push(v); frame.push(v); }
            case JVMConstants.SWAP -> { Object v1 = frame.pop(); Object v2 = frame.pop(); frame.push(v1); frame.push(v2); }

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

            case JVMConstants.ISHL -> { int v2 = frame.popInt(); int v1 = frame.popInt(); frame.pushInt(v1 << v2); }
            case JVMConstants.ISHR -> { int v2 = frame.popInt(); int v1 = frame.popInt(); frame.pushInt(v1 >> v2); }
            case JVMConstants.IUSHR -> { int v2 = frame.popInt(); int v1 = frame.popInt(); frame.pushInt(v1 >>> v2); }
            case JVMConstants.IAND -> { int v2 = frame.popInt(); int v1 = frame.popInt(); frame.pushInt(v1 & v2); }
            case JVMConstants.IOR -> { int v2 = frame.popInt(); int v1 = frame.popInt(); frame.pushInt(v1 | v2); }
            case JVMConstants.IXOR -> { int v2 = frame.popInt(); int v1 = frame.popInt(); frame.pushInt(v1 ^ v2); }
            case JVMConstants.LSHL -> { int v2 = frame.popInt(); long v1 = frame.popLong(); frame.pushLong(v1 << v2); }
            case JVMConstants.LSHR -> { int v2 = frame.popInt(); long v1 = frame.popLong(); frame.pushLong(v1 >> v2); }
            case JVMConstants.LUSHR -> { int v2 = frame.popInt(); long v1 = frame.popLong(); frame.pushLong(v1 >>> v2); }
            case JVMConstants.LAND -> { long v2 = frame.popLong(); long v1 = frame.popLong(); frame.pushLong(v1 & v2); }
            case JVMConstants.LOR -> { long v2 = frame.popLong(); long v1 = frame.popLong(); frame.pushLong(v1 | v2); }
            case JVMConstants.LXOR -> { long v2 = frame.popLong(); long v1 = frame.popLong(); frame.pushLong(v1 ^ v2); }

            case JVMConstants.IFEQ -> { int offset = reader.readShort(); if (frame.popInt() == 0) reader.skip(offset - 3); }
            case JVMConstants.IFNE -> { int offset = reader.readShort(); if (frame.popInt() != 0) reader.skip(offset - 3); }
            case JVMConstants.IFLT -> { int offset = reader.readShort(); if (frame.popInt() < 0) reader.skip(offset - 3); }
            case JVMConstants.IFGE -> { int offset = reader.readShort(); if (frame.popInt() >= 0) reader.skip(offset - 3); }
            case JVMConstants.IFGT -> { int offset = reader.readShort(); if (frame.popInt() > 0) reader.skip(offset - 3); }
            case JVMConstants.IFLE -> { int offset = reader.readShort(); if (frame.popInt() <= 0) reader.skip(offset - 3); }

            case JVMConstants.LCMP -> {
                long v2 = frame.popLong();
                long v1 = frame.popLong();
                frame.pushInt(v1 > v2 ? 1 : (v1 < v2 ? -1 : 0));
            }
            case JVMConstants.FCMPL -> {
                float v2 = ((Float) frame.pop()).floatValue();
                float v1 = ((Float) frame.pop()).floatValue();
                frame.pushInt(v1 < v2 ? -1 : (v1 > v2 ? 1 : -1));
            }
            case JVMConstants.FCMPG -> {
                float v2 = ((Float) frame.pop()).floatValue();
                float v1 = ((Float) frame.pop()).floatValue();
                frame.pushInt(v1 > v2 ? 1 : (v1 < v2 ? -1 : 1));
            }
            case JVMConstants.DCMPL -> {
                double v2 = ((Double) frame.pop()).doubleValue();
                double v1 = ((Double) frame.pop()).doubleValue();
                frame.pushInt(v1 < v2 ? -1 : (v1 > v2 ? 1 : -1));
            }
            case JVMConstants.DCMPG -> {
                double v2 = ((Double) frame.pop()).doubleValue();
                double v1 = ((Double) frame.pop()).doubleValue();
                frame.pushInt(v1 > v2 ? 1 : (v1 < v2 ? -1 : 1));
            }

            case JVMConstants.I2L -> frame.pushLong((long) frame.popInt());
            case JVMConstants.I2F -> frame.push(Float.valueOf((float) frame.popInt()));
            case JVMConstants.I2D -> frame.push(Double.valueOf((double) frame.popInt()));
            case JVMConstants.L2I -> frame.pushInt((int) frame.popLong());
            case JVMConstants.L2F -> frame.push(Float.valueOf((float) frame.popLong()));
            case JVMConstants.L2D -> frame.push(Double.valueOf((double) frame.popLong()));
            case JVMConstants.F2I -> frame.pushInt((int) ((Float) frame.pop()).floatValue());
            case JVMConstants.F2L -> frame.pushLong((long) ((Float) frame.pop()).floatValue());
            case JVMConstants.F2D -> frame.push(Double.valueOf((double) ((Float) frame.pop()).floatValue()));
            case JVMConstants.D2I -> frame.pushInt((int) ((Double) frame.pop()).doubleValue());
            case JVMConstants.D2L -> frame.pushLong((long) ((Double) frame.pop()).doubleValue());
            case JVMConstants.D2F -> frame.push(Float.valueOf((float) ((Double) frame.pop()).doubleValue()));

            case JVMConstants.GOTO -> reader.skip(reader.readShort() - 3);
            case JVMConstants.RETURN -> throw new ReturnException(null);
            case JVMConstants.IRETURN -> throw new ReturnException(frame.popInt());
            case JVMConstants.LRETURN -> throw new ReturnException(frame.popLong());
            case JVMConstants.FRETURN -> throw new ReturnException(frame.pop());
            case JVMConstants.DRETURN -> throw new ReturnException(frame.pop());
            case JVMConstants.ARETURN -> throw new ReturnException(frame.pop());

            case JVMConstants.INVOKESTATIC -> {
                int methodRef = reader.readUnsignedShort();
                invokeStatic(frame, methodRef, thread);
            }
            case JVMConstants.INVOKEVIRTUAL -> {
                int methodRef = reader.readUnsignedShort();
                invokeVirtual(frame, methodRef, thread);
            }
            case JVMConstants.INVOKESPECIAL -> {
                int methodRef = reader.readUnsignedShort();
                invokeSpecial(frame, methodRef, thread);
            }
            case JVMConstants.INVOKEINTERFACE -> {
                reader.readUnsignedShort();
                reader.readUnsignedByte();
                reader.readUnsignedByte();
            }

            case JVMConstants.NEW -> {
                int classIndex = reader.readUnsignedShort();
                HeapObject obj = createNewInstance(frame, classIndex);
                frame.push(obj);
            }
            case JVMConstants.NEWARRAY -> frame.push("array_" + reader.readUnsignedByte() + "_" + frame.popInt());
            case JVMConstants.ARRAYLENGTH -> {
                Object arr = frame.pop();
                if (arr instanceof HeapObject) {
                    frame.pushInt(0);
                } else {
                    frame.pushInt(0);
                }
            }

            case JVMConstants.GETSTATIC -> frame.push("static_" + reader.readUnsignedShort());
            case JVMConstants.PUTSTATIC -> frame.pop();
            case JVMConstants.GETFIELD -> {
                int fieldRef = reader.readUnsignedShort();
                Object obj = frame.pop();
                Object value = getFieldValue(frame, obj, fieldRef);
                frame.push(value);
            }
            case JVMConstants.PUTFIELD -> {
                int fieldRef = reader.readUnsignedShort();
                Object value = frame.pop();
                Object obj = frame.pop();
                putFieldValue(frame, obj, fieldRef, value);
            }

            default -> logger.trace("Unhandled opcode: 0x{}", Integer.toHexString(opcode));
        }
    }

    private void invokeStatic(StackFrame frame, int methodRef, MingshaThread thread) {
        ConstantPool cp = frame.getConstantPool();
        if (cp == null) {
            logger.warn("No constant pool in frame for INVOKESTATIC");
            return;
        }

        String className = cp.getMethodClassName(methodRef);
        String methodName = cp.getMethodName(methodRef);
        String methodDesc = cp.getMethodDescriptor(methodRef);

        logger.debug("INVOKESTATIC: {} {} from class {}", methodName, methodDesc, className);

        KlassModel klass = methodArea.getKlass(className);
        if (klass == null) {
            logger.warn("Class not found in method area: {}", className);
            return;
        }

        KlassModel.MethodInfo method = klass.findMethod(methodName, methodDesc);
        if (method == null) {
            logger.warn("Method not found: {} {} in class {}", methodName, methodDesc, className);
            return;
        }

        StackFrame newFrame = new StackFrame(method.maxLocals, method.maxStack);
        newFrame.setMethodName(methodName);
        newFrame.setClassName(className);
        newFrame.setCurrentKlass(klass);
        newFrame.setConstantPool(cp);
        newFrame.setNextFrame(frame);

        thread.getStack().pushFrame(newFrame);

        try {
            interpretMethod(thread, klass, method);
        } catch (ReturnException e) {
            frame.setReturnValue(e.returnValue);
        }
    }

    private void invokeVirtual(StackFrame frame, int methodRef, MingshaThread thread) {
        Object objRef = frame.pop();
        if (objRef == null) {
            logger.warn("INVOKEVIRTUAL on null reference");
            return;
        }

        if (!(objRef instanceof HeapObject)) {
            logger.warn("INVOKEVIRTUAL on non-HeapObject: {}", objRef.getClass());
            return;
        }

        HeapObject heapObj = (HeapObject) objRef;
        ConstantPool cp = frame.getConstantPool();
        if (cp == null) {
            return;
        }

        String methodName = cp.getMethodName(methodRef);
        String methodDesc = cp.getMethodDescriptor(methodRef);

        logger.debug("INVOKEVIRTUAL: {} {} on object {}", methodName, methodDesc, heapObj);

        KlassModel.MethodInfo method = methodResolver.resolveMethod(heapObj.getKlass(), methodName, methodDesc);
        if (method == null) {
            logger.warn("Method not found: {} {}", methodName, methodDesc);
            return;
        }

        StackFrame newFrame = new StackFrame(method.maxLocals, method.maxStack);
        newFrame.setMethodName(methodName);
        newFrame.setClassName(heapObj.getKlass().getName());
        newFrame.setCurrentKlass(heapObj.getKlass());
        newFrame.setConstantPool(cp);
        newFrame.setNextFrame(frame);

        thread.getStack().pushFrame(newFrame);

        try {
            interpretMethod(thread, heapObj.getKlass(), method);
        } catch (ReturnException e) {
            frame.setReturnValue(e.returnValue);
        }
    }

    private void invokeSpecial(StackFrame frame, int methodRef, MingshaThread thread) {
        ConstantPool cp = frame.getConstantPool();
        if (cp == null) {
            return;
        }

        String className = cp.getMethodClassName(methodRef);
        String methodName = cp.getMethodName(methodRef);
        String methodDesc = cp.getMethodDescriptor(methodRef);

        logger.debug("INVOKESPECIAL: {} {} from class {}", methodName, methodDesc, className);

        KlassModel klass = methodArea.getKlass(className);
        if (klass == null) {
            logger.warn("Class not found: {}", className);
            return;
        }

        KlassModel.MethodInfo method = klass.findMethod(methodName, methodDesc);
        if (method == null) {
            logger.warn("Method not found: {} {}", methodName, methodDesc);
            return;
        }

        StackFrame newFrame = new StackFrame(method.maxLocals, method.maxStack);
        newFrame.setMethodName(methodName);
        newFrame.setClassName(className);
        newFrame.setCurrentKlass(klass);
        newFrame.setConstantPool(cp);
        newFrame.setNextFrame(frame);

        thread.getStack().pushFrame(newFrame);

        try {
            interpretMethod(thread, klass, method);
        } catch (ReturnException e) {
            frame.setReturnValue(e.returnValue);
        }
    }

    private void interpretMethod(MingshaThread thread, KlassModel klass, KlassModel.MethodInfo method) {
        BytecodeReader reader = new BytecodeReader(method.bytecode);
        StackFrame frame = thread.getStack().getTopFrame();
        int instructionCount = 0;

        while (reader.hasRemaining()) {
            int opcode = reader.readUnsignedByte();
            int pc = reader.getPosition() - 1;
            frame.setCurrentPc(pc);

            executeInstruction(opcode, frame, reader, thread);
            instructionCount++;

            if (instructionCount > 100000) {
                throw new RuntimeException("Possible infinite loop in " + method.name);
            }
        }
    }

    private HeapObject createNewInstance(StackFrame frame, int classIndex) {
        ConstantPool cp = frame.getConstantPool();
        if (cp == null) {
            logger.warn("No constant pool for NEW");
            return null;
        }

        String className = cp.getClassName(classIndex);
        if (className == null) {
            logger.warn("Could not resolve class from index {}", classIndex);
            return null;
        }

        logger.debug("Creating new instance of: {}", className);

        KlassModel klass = methodArea.getKlass(className);
        if (klass == null) {
            logger.warn("Class not loaded in method area: {}", className);
            klass = new KlassModel(className, "java/lang/Object", JVMConstants.ACC_PUBLIC);
            methodArea.addKlass(klass);
        }

        HeapObject obj = new HeapObject(klass);
        return obj;
    }

    private Object getFieldValue(StackFrame frame, Object obj, int fieldRef) {
        if (obj instanceof HeapObject) {
            HeapObject heapObj = (HeapObject) obj;
            ConstantPool cp = frame.getConstantPool();
            if (cp != null) {
                String fieldName = cp.getFieldName(fieldRef);
                if (fieldName != null) {
                    return heapObj.getField(fieldName);
                }
            }
        }
        return null;
    }

    private void putFieldValue(StackFrame frame, Object obj, int fieldRef, Object value) {
        if (obj instanceof HeapObject) {
            HeapObject heapObj = (HeapObject) obj;
            ConstantPool cp = frame.getConstantPool();
            if (cp != null) {
                String fieldName = cp.getFieldName(fieldRef);
                if (fieldName != null) {
                    heapObj.setField(fieldName, value);
                }
            }
        }
    }

    public static class ReturnException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public final Object returnValue;
        public ReturnException(Object returnValue) { this.returnValue = returnValue; }
    }
}
