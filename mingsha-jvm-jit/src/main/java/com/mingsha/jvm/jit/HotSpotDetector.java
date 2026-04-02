package com.mingsha.jvm.jit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Hot spot detector for JIT compilation.
 * <p>
 * Tracks method invocation counts to identify hot spots.
 *
 * @version 1.0.0
 */
public class HotSpotDetector {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(HotSpotDetector.class);

    /** Invocation threshold for compilation */
    private final int threshold;

    /** Method invocation counts */
    private final Map<String, Integer> methodInvocationCount = new ConcurrentHashMap<>();

    /**
     * Constructor with threshold.
     *
     * @param threshold compilation threshold
     */
    public HotSpotDetector(int threshold) {
        this.threshold = threshold;
        logger.info("HotSpotDetector created with threshold={}", threshold);
    }

    /**
     * Records a method invocation.
     *
     * @param methodName method name
     */
    public void recordInvocation(String methodName) {
        int count = methodInvocationCount.getOrDefault(methodName, 0) + 1;
        methodInvocationCount.put(methodName, count);
        logger.trace("Invocation recorded: {} count={}", methodName, count);
    }

    /**
     * Checks if method is hot spot.
     *
     * @param methodName method name
     * @return true if hot
     */
    public boolean isHotSpot(String methodName) {
        int count = methodInvocationCount.getOrDefault(methodName, 0);
        boolean isHot = count >= threshold;
        if (isHot) {
            logger.debug("Hot spot detected: {} (count={})", methodName, count);
        }
        return isHot;
    }

    /**
     * Returns invocation count for method.
     *
     * @param methodName method name
     * @return invocation count
     */
    public int getInvocationCount(String methodName) {
        return methodInvocationCount.getOrDefault(methodName, 0);
    }

    /**
     * Resets invocation count.
     *
     * @param methodName method name
     */
    public void reset(String methodName) {
        methodInvocationCount.put(methodName, 0);
        logger.debug("Reset invocation count for: {}", methodName);
    }
}
