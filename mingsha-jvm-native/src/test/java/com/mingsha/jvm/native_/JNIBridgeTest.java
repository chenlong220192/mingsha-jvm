package com.mingsha.jvm.native_;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JNIBridgeTest {

    @Test
    void testGetInstance() {
        JNIBridge bridge = JNIBridge.getInstance();
        assertNotNull(bridge);
        assertSame(bridge, JNIBridge.getInstance());
    }

    @Test
    void testFindNativeMethod() {
        JNIBridge bridge = JNIBridge.getInstance();
        assertNotNull(bridge);
        assertThrows(UnsupportedOperationException.class, 
            () -> bridge.invokeNative("java.lang.Object", "hashCode", "()I", null));
    }

    @Test
    void testRegisterNative() {
        JNIBridge bridge = JNIBridge.getInstance();
        JNIBridge.NativeMethod method = args -> 42;
        assertDoesNotThrow(() -> bridge.registerNative("Test", "test", "()I", method));
    }
}