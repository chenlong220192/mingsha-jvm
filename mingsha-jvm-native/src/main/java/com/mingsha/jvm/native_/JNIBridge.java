package com.mingsha.jvm.native_;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JNI Bridge for native method simulation.
 * <p>
 * Simulates JNI native method invocation.
 *
 * @version 1.0.0
 */
public class JNIBridge {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(JNIBridge.class);

    /** Singleton instance */
    private static final JNIBridge INSTANCE = new JNIBridge();

    /** Native method registry */
    private final Map<String, NativeMethod> nativeMethods = new ConcurrentHashMap<>();

    /** Private constructor for singleton */
    private JNIBridge() {
        registerSimulatedNatives();
        logger.info("JNIBridge initialized with {} methods", nativeMethods.size());
    }

    /** @return singleton instance */
    public static JNIBridge getInstance() { return INSTANCE; }

    /**
     * Registers a native method.
     */
    public void registerNative(String className, String methodName, String signature, NativeMethod method) {
        String key = className + "." + methodName + signature;
        nativeMethods.put(key, method);
        logger.debug("Registered native: {}{}", methodName, signature);
    }

    /**
     * Invokes a native method.
     */
    public Object invokeNative(String className, String methodName, String signature, Object[] args) {
        String key = className + "." + methodName + signature;
        NativeMethod method = nativeMethods.get(key);
        if (method != null) {
            logger.debug("Invoking native: {}{}", methodName, signature);
            return method.invoke(args);
        }
        logger.warn("Native method not found: {}{}", methodName, signature);
        throw new UnsupportedOperationException("Native method not found: " + className + "." + methodName + signature);
    }

    /** Registers simulated native methods */
    private void registerSimulatedNatives() {
        registerNative("java/lang/Object", "hashCode", "()I", args -> 0);
        registerNative("java/lang/Object", "getClass", "()Ljava/lang/Class;", args -> null);
        registerNative("java/lang/Object", "clone", "()Ljava/lang/Object;", args -> null);
        registerNative("java/lang/System", "currentTimeMillis", "()J", args -> System.currentTimeMillis());
        registerNative("java/lang/System", "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V", args -> {
            if (args == null || args.length < 5) {
                throw new NullPointerException("System.arraycopy requires non-null arguments");
            }
            try {
                System.arraycopy(args[0], ((Number)args[1]).intValue(), 
                                args[2], ((Number)args[3]).intValue(), 
                                ((Number)args[4]).intValue());
            } catch (Exception e) {
                logger.error("System.arraycopy failed", e);
            }
            return null;
        });
        registerNative("java/io/PrintStream", "println", "(Ljava/lang/String;)V", args -> {
            if (args != null && args.length > 0 && args[0] != null) {
                System.out.println(args[0]);
            } else {
                System.out.println();
            }
            return null;
        });
        logger.debug("Registered {} simulated native methods", nativeMethods.size());
    }

    /** Functional interface for native methods */
    @FunctionalInterface
    public interface NativeMethod {
        Object invoke(Object[] args);
    }
}
