package com.mingsha.jvm.jit;

/**
 * Hot spot detector for JIT compilation.
 */
public class HotSpotDetector {
    private final int threshold;
    private final java.util.Map<String, Integer> methodInvocationCount = new java.util.HashMap<>();

    public HotSpotDetector(int threshold) {
        this.threshold = threshold;
    }

    public void recordInvocation(String methodName) {
        int count = methodInvocationCount.getOrDefault(methodName, 0) + 1;
        methodInvocationCount.put(methodName, count);
    }

    public boolean isHotSpot(String methodName) {
        return methodInvocationCount.getOrDefault(methodName, 0) >= threshold;
    }

    public int getInvocationCount(String methodName) {
        return methodInvocationCount.getOrDefault(methodName, 0);
    }

    public void reset(String methodName) {
        methodInvocationCount.put(methodName, 0);
    }
}
