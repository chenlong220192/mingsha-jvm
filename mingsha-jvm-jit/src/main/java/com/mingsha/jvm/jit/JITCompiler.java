package com.mingsha.jvm.jit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mingsha.jvm.runtime.methodarea.KlassModel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JIT Compiler for hot methods.
 * <p>
 * Caches compiled bytecode for frequently executed methods.
 *
 * @version 1.0.0
 */
public class JITCompiler {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(JITCompiler.class);

    /** Hot spot detector */
    private final HotSpotDetector hotSpotDetector;

    /** Compilation cache */
    private final Map<String, CompiledMethod> compilationCache = new ConcurrentHashMap<>();

    /**
     * Constructor with hot spot detector.
     *
     * @param hotSpotDetector the hot spot detector
     */
    public JITCompiler(HotSpotDetector hotSpotDetector) {
        this.hotSpotDetector = hotSpotDetector;
        logger.info("JITCompiler initialized");
    }

    /**
     * Checks if method should be compiled.
     *
     * @param methodName method name
     * @return true if should compile
     */
    public boolean shouldCompile(String methodName) {
        boolean should = hotSpotDetector.isHotSpot(methodName) && !compilationCache.containsKey(methodName);
        logger.debug("shouldCompile({}) = {}", methodName, should);
        return should;
    }

    /**
     * Compiles a method.
     *
     * @param methodName method name
     * @param method method info
     * @param bytecode bytecode
     * @return compiled method
     */
    public CompiledMethod compile(String methodName, KlassModel.MethodInfo method, byte[] bytecode) {
        logger.info("Compiling method: {}", methodName);
        CompiledMethod compiled = new CompiledMethod(methodName, bytecode);
        compilationCache.put(methodName, compiled);
        logger.debug("Method {} compiled and cached", methodName);
        return compiled;
    }

    /**
     * Gets compiled method.
     *
     * @param methodName method name
     * @return compiled method or null
     */
    public CompiledMethod getCompiledMethod(String methodName) {
        return compilationCache.get(methodName);
    }

    /** @return compilation cache size */
    public int getCacheSize() { return compilationCache.size(); }

    /**
     * Compiled method wrapper.
     */
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
