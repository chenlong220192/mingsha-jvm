package com.mingsha.jvm.jit;

import com.mingsha.jvm.runtime.methodarea.KlassModel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JIT Compiler for hot methods.
 */
public class JITCompiler {
    private final HotSpotDetector hotSpotDetector;
    private final Map<String, CompiledMethod> compilationCache = new ConcurrentHashMap<>();

    public JITCompiler(HotSpotDetector hotSpotDetector) {
        this.hotSpotDetector = hotSpotDetector;
    }

    public boolean shouldCompile(String methodName) {
        return hotSpotDetector.isHotSpot(methodName) && !compilationCache.containsKey(methodName);
    }

    public CompiledMethod compile(String methodName, KlassModel.MethodInfo method, byte[] bytecode) {
        if (compilationCache.containsKey(methodName)) {
            return compilationCache.get(methodName);
        }
        // Simplified: just cache the bytecode for now
        CompiledMethod compiled = new CompiledMethod(methodName, bytecode);
        compilationCache.put(methodName, compiled);
        return compiled;
    }

    public CompiledMethod getCompiledMethod(String methodName) {
        return compilationCache.get(methodName);
    }

    public static class CompiledMethod {
        public final String methodName;
        public final byte[] bytecode;
        public final long compileTime;

        public CompiledMethod(String methodName, byte[] bytecode) {
            this.methodName = methodName;
            this.bytecode = bytecode;
            this.compileTime = System.currentTimeMillis();
        }
    }
}
