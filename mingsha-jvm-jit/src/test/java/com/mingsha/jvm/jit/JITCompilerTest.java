package com.mingsha.jvm.jit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.mingsha.jvm.runtime.methodarea.KlassModel;

class JITCompilerTest {

    @Test
    void testJITCompilerCreation() {
        HotSpotDetector detector = new HotSpotDetector(100);
        JITCompiler compiler = new JITCompiler(detector);
        assertNotNull(compiler);
    }

    @Test
    void testShouldCompile() {
        HotSpotDetector detector = new HotSpotDetector(10);
        JITCompiler compiler = new JITCompiler(detector);
        for (int i = 0; i < 15; i++) {
            detector.recordInvocation("hotMethod");
        }
        assertTrue(compiler.shouldCompile("hotMethod"));
    }

    @Test
    void testCompile() {
        HotSpotDetector detector = new HotSpotDetector(100);
        JITCompiler compiler = new JITCompiler(detector);
        KlassModel.MethodInfo method = new KlassModel.MethodInfo("test", "()V", 1);
        byte[] bytecode = new byte[] { 0x00 };

        JITCompiler.CompiledMethod compiled = compiler.compile("testMethod", method, bytecode);
        assertNotNull(compiled);
        assertEquals("testMethod", compiled.methodName);
    }

    @Test
    void testGetCompiledMethod() {
        HotSpotDetector detector = new HotSpotDetector(100);
        JITCompiler compiler = new JITCompiler(detector);
        KlassModel.MethodInfo method = new KlassModel.MethodInfo("test", "()V", 1);
        byte[] bytecode = new byte[] { 0x00 };

        compiler.compile("testMethod", method, bytecode);
        assertNotNull(compiler.getCompiledMethod("testMethod"));
        assertNull(compiler.getCompiledMethod("nonExistent"));
    }

    @Test
    void testCacheSize() {
        HotSpotDetector detector = new HotSpotDetector(100);
        JITCompiler compiler = new JITCompiler(detector);
        KlassModel.MethodInfo method1 = new KlassModel.MethodInfo("test1", "()V", 1);
        KlassModel.MethodInfo method2 = new KlassModel.MethodInfo("test2", "()V", 1);
        byte[] bytecode = new byte[] { 0x00 };

        assertEquals(0, compiler.getCacheSize());
        compiler.compile("method1", method1, bytecode);
        assertEquals(1, compiler.getCacheSize());
        compiler.compile("method2", method2, bytecode);
        assertEquals(2, compiler.getCacheSize());
    }

    @Test
    void testCompiledMethodFields() {
        HotSpotDetector detector = new HotSpotDetector(100);
        JITCompiler compiler = new JITCompiler(detector);
        byte[] bytecode = new byte[] { 0x01, 0x02, 0x03 };

        JITCompiler.CompiledMethod compiled = compiler.compile("test", null, bytecode);
        assertEquals("test", compiled.methodName);
        assertArrayEquals(bytecode, compiled.bytecode);
        assertTrue(compiled.compileTime > 0);
    }
}