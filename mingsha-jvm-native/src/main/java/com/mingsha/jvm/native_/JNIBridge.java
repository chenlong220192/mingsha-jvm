package com.mingsha.jvm.native_;

import java.util.HashMap;
import java.util.Map;

/**
 * JNI Bridge for native method simulation.
 */
public class JNIBridge {
    private static final JNIBridge INSTANCE = new JNIBridge();
    private final Map<String, NativeMethod> nativeMethods = new HashMap<>();

    private JNIBridge() {
        registerSimulatedNatives();
    }

    public static JNIBridge getInstance() { return INSTANCE; }

    public void registerNative(String className, String methodName, String signature, NativeMethod method) {
        nativeMethods.put(className + "." + methodName + signature, method);
    }

    public Object invokeNative(String className, String methodName, String signature, Object[] args) {
        NativeMethod method = nativeMethods.get(className + "." + methodName + signature);
        if (method != null) {
            return method.invoke(args);
        }
        throw new UnsupportedOperationException("Native method not found: " + className + "." + methodName);
    }

    private void registerSimulatedNatives() {
        // Simulated Object methods
        registerNative("java/lang/Object", "hashCode", "()I", args -> 0);
        registerNative("java/lang/Object", "getClass", "()Ljava/lang/Class;", args -> null);
        registerNative("java/lang/Object", "clone", "()Ljava/lang/Object;", args -> null);
        // Simulated System methods
        registerNative("java/lang/System", "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V", args -> { return null; });
    }

    @FunctionalInterface
    public interface NativeMethod {
        Object invoke(Object[] args);
    }
}
