package com.mingsha.jvm.boot;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.mingsha.jvm.core.constants.JVMConstants;
import com.mingsha.jvm.interpreter.LoopInterpreter;
import com.mingsha.jvm.runtime.thread.MingshaThread;
import com.mingsha.jvm.runtime.methodarea.KlassModel;
import com.mingsha.jvm.runtime.stack.StackFrame;

class L4TestSuite {

    @Test
    void testArithmeticBytecodeExecutes() {
        MingshaThread thread = new MingshaThread("TestThread", Thread.NORM_PRIORITY);
        KlassModel klass = createArithmeticTestClass();
        
        KlassModel.MethodInfo method = findMethod(klass, "test");
        assertNotNull(method);
        
        StackFrame frame = new StackFrame(method.maxLocals, method.maxStack);
        frame.setMethodName("test");
        thread.getStack().pushFrame(frame);
        
        assertDoesNotThrow(() -> {
            LoopInterpreter interpreter = LoopInterpreter.getInstance();
            interpreter.interpret(thread, klass, method, method.bytecode);
        });
    }

    @Test
    void testConditionalBytecodeExecutes() {
        MingshaThread thread = new MingshaThread("TestThread", Thread.NORM_PRIORITY);
        KlassModel klass = createConditionalTestClass();
        
        KlassModel.MethodInfo method = findMethod(klass, "test");
        assertNotNull(method);
        
        StackFrame frame = new StackFrame(method.maxLocals, method.maxStack);
        frame.setMethodName("test");
        thread.getStack().pushFrame(frame);
        
        assertDoesNotThrow(() -> {
            LoopInterpreter interpreter = LoopInterpreter.getInstance();
            interpreter.interpret(thread, klass, method, method.bytecode);
        });
    }

    @Test
    void testLoopBytecodeExecutes() {
        MingshaThread thread = new MingshaThread("TestThread", Thread.NORM_PRIORITY);
        KlassModel klass = createLoopTestClass();
        
        KlassModel.MethodInfo method = findMethod(klass, "test");
        assertNotNull(method);
        
        StackFrame frame = new StackFrame(method.maxLocals, method.maxStack);
        frame.setMethodName("test");
        thread.getStack().pushFrame(frame);
        
        assertDoesNotThrow(() -> {
            LoopInterpreter interpreter = LoopInterpreter.getInstance();
            interpreter.interpret(thread, klass, method, method.bytecode);
        });
    }

    @Test
    void testMethodCallBytecodeExecutes() {
        MingshaThread thread = new MingshaThread("TestThread", Thread.NORM_PRIORITY);
        KlassModel klass = createMethodCallTestClass();
        
        KlassModel.MethodInfo method = findMethod(klass, "main");
        assertNotNull(method);
        
        StackFrame frame = new StackFrame(method.maxLocals, method.maxStack);
        frame.setMethodName("main");
        thread.getStack().pushFrame(frame);
        
        assertDoesNotThrow(() -> {
            LoopInterpreter interpreter = LoopInterpreter.getInstance();
            interpreter.interpret(thread, klass, method, method.bytecode);
        });
    }

    @Test
    void testFieldAccessBytecodeExecutes() {
        MingshaThread thread = new MingshaThread("TestThread", Thread.NORM_PRIORITY);
        KlassModel klass = createFieldAccessTestClass();
        
        KlassModel.MethodInfo method = findMethod(klass, "test");
        assertNotNull(method);
        
        StackFrame frame = new StackFrame(method.maxLocals, method.maxStack);
        frame.setMethodName("test");
        thread.getStack().pushFrame(frame);
        
        assertDoesNotThrow(() -> {
            LoopInterpreter interpreter = LoopInterpreter.getInstance();
            interpreter.interpret(thread, klass, method, method.bytecode);
        });
    }

    private KlassModel createArithmeticTestClass() {
        KlassModel klass = new KlassModel("ArithmeticTest", "java/lang/Object", JVMConstants.ACC_PUBLIC);
        
        KlassModel.MethodInfo method = new KlassModel.MethodInfo(
            "test", "()V", JVMConstants.ACC_PUBLIC | JVMConstants.ACC_STATIC);
        method.maxStack = 3;
        method.maxLocals = 2;
        
        byte[] bytecode = new byte[] {
            (byte)JVMConstants.ICONST_5,
            (byte)JVMConstants.ISTORE, (byte)0,
            (byte)JVMConstants.ILOAD, (byte)0,
            (byte)JVMConstants.ICONST_3,
            (byte)JVMConstants.IADD,
            (byte)JVMConstants.POP,
            (byte)JVMConstants.RETURN
        };
        
        method.bytecode = bytecode;
        klass.addMethod(method);
        return klass;
    }

    private KlassModel createConditionalTestClass() {
        KlassModel klass = new KlassModel("ConditionalTest", "java/lang/Object", JVMConstants.ACC_PUBLIC);
        
        KlassModel.MethodInfo method = new KlassModel.MethodInfo(
            "test", "()V", JVMConstants.ACC_PUBLIC | JVMConstants.ACC_STATIC);
        method.maxStack = 3;
        method.maxLocals = 1;
        
        byte[] bytecode = new byte[] {
            (byte)JVMConstants.ICONST_1,
            (byte)JVMConstants.ISTORE, (byte)0,
            (byte)JVMConstants.ILOAD, (byte)0,
            (byte)JVMConstants.IFEQ, (byte)0, (byte)5,
            (byte)JVMConstants.RETURN
        };
        
        method.bytecode = bytecode;
        klass.addMethod(method);
        return klass;
    }

    private KlassModel createLoopTestClass() {
        KlassModel klass = new KlassModel("LoopTest", "java/lang/Object", JVMConstants.ACC_PUBLIC);
        
        KlassModel.MethodInfo method = new KlassModel.MethodInfo(
            "test", "()V", JVMConstants.ACC_PUBLIC | JVMConstants.ACC_STATIC);
        method.maxStack = 3;
        method.maxLocals = 2;
        
        byte[] bytecode = new byte[] {
            (byte)JVMConstants.ICONST_0,
            (byte)JVMConstants.ISTORE, (byte)0,
            (byte)JVMConstants.ILOAD, (byte)0,
            (byte)JVMConstants.ICONST_3,
            (byte)JVMConstants.IFLE, (byte)0, (byte)7,
            (byte)JVMConstants.IINC, (byte)0, (byte)1,
            (byte)JVMConstants.GOTO, (byte)0, (byte)-9,
            (byte)JVMConstants.RETURN
        };
        
        method.bytecode = bytecode;
        klass.addMethod(method);
        return klass;
    }

    private KlassModel createMethodCallTestClass() {
        KlassModel klass = new KlassModel("MethodCallTest", "java/lang/Object", JVMConstants.ACC_PUBLIC);
        
        KlassModel.MethodInfo method = new KlassModel.MethodInfo(
            "main", "()V", JVMConstants.ACC_PUBLIC | JVMConstants.ACC_STATIC);
        method.maxStack = 3;
        method.maxLocals = 0;
        
        byte[] bytecode = new byte[] {
            (byte)JVMConstants.ICONST_5,
            (byte)JVMConstants.ICONST_3,
            (byte)JVMConstants.INVOKESTATIC, (byte)0, (byte)1,
            (byte)JVMConstants.POP,
            (byte)JVMConstants.RETURN
        };
        
        method.bytecode = bytecode;
        klass.addMethod(method);
        return klass;
    }

    private KlassModel createFieldAccessTestClass() {
        KlassModel klass = new KlassModel("FieldAccessTest", "java/lang/Object", JVMConstants.ACC_PUBLIC);
        
        KlassModel.MethodInfo method = new KlassModel.MethodInfo(
            "test", "()V", JVMConstants.ACC_PUBLIC | JVMConstants.ACC_STATIC);
        method.maxStack = 2;
        method.maxLocals = 0;
        
        byte[] bytecode = new byte[] {
            (byte)JVMConstants.GETSTATIC, (byte)0, (byte)1,
            (byte)JVMConstants.POP,
            (byte)JVMConstants.RETURN
        };
        
        method.bytecode = bytecode;
        klass.addMethod(method);
        return klass;
    }

    private KlassModel.MethodInfo findMethod(KlassModel klass, String name) {
        for (KlassModel.MethodInfo method : klass.getMethods().values()) {
            if (name.equals(method.name)) {
                return method;
            }
        }
        return null;
    }
}
