package com.mingsha.jvm.interpreter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.mingsha.jvm.core.constants.JVMConstants;
import com.mingsha.jvm.runtime.thread.MingshaThread;
import com.mingsha.jvm.runtime.methodarea.KlassModel;

class LoopInterpreterTest {

    @Test
    void testGetInstance() {
        LoopInterpreter interpreter = LoopInterpreter.getInstance();
        assertNotNull(interpreter);
        assertSame(interpreter, LoopInterpreter.getInstance());
    }

    @Test
    void testGetHotSpotThreshold() {
        LoopInterpreter interpreter = LoopInterpreter.getInstance();
        assertTrue(interpreter.getHotSpotThreshold() > 0);
    }

    @Test
    void testInterpretWithSimpleBytecode() {
        LoopInterpreter interpreter = LoopInterpreter.getInstance();
        MingshaThread thread = new MingshaThread("TestThread", Thread.NORM_PRIORITY);

        KlassModel klass = new KlassModel("TestClass", "java/lang/Object", 1);
        KlassModel.MethodInfo method = new KlassModel.MethodInfo("test", "()V", 1);
        method.maxStack = 5;
        method.maxLocals = 2;

        byte[] bytecode = new byte[] {
            (byte)4,
            (byte)5,
            (byte)96,
            (byte)177
        };

        assertDoesNotThrow(() -> interpreter.interpret(thread, klass, method, bytecode));
    }

    @Test
    void testInterpretWithILoadAndIStore() {
        LoopInterpreter interpreter = LoopInterpreter.getInstance();
        MingshaThread thread = new MingshaThread("TestThread", Thread.NORM_PRIORITY);

        KlassModel klass = new KlassModel("TestClass", "java/lang/Object", 1);
        KlassModel.MethodInfo method = new KlassModel.MethodInfo("test", "()V", 1);
        method.maxStack = 5;
        method.maxLocals = 4;

        byte[] bytecode = new byte[] {
            (byte)5,
            (byte)54, (byte)0,
            (byte)21, (byte)0,
            (byte)177
        };

        assertDoesNotThrow(() -> interpreter.interpret(thread, klass, method, bytecode));
    }

    @Test
    void testInterpretWithConditionalBranch() {
        LoopInterpreter interpreter = LoopInterpreter.getInstance();
        MingshaThread thread = new MingshaThread("TestThread", Thread.NORM_PRIORITY);

        KlassModel klass = new KlassModel("TestClass", "java/lang/Object", 1);
        KlassModel.MethodInfo method = new KlassModel.MethodInfo("test", "()V", 1);
        method.maxStack = 5;
        method.maxLocals = 2;

        byte[] bytecode = new byte[] {
            (byte)3,
            (byte)153, (byte)0, (byte)5,
            (byte)177
        };

        assertDoesNotThrow(() -> interpreter.interpret(thread, klass, method, bytecode));
    }

    @Test
    void testReturnException() {
        LoopInterpreter.ReturnException ex = new LoopInterpreter.ReturnException(42);
        assertEquals(42, ex.returnValue);
    }
}