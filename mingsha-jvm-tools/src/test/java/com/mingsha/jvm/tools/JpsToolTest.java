package com.mingsha.jvm.tools;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JpsToolTest {

    @Test
    void testJvmProcessCreation() {
        com.mingsha.jvm.tools.jps.JpsTool.JvmProcess process = 
            new com.mingsha.jvm.tools.jps.JpsTool.JvmProcess(12345, "boot", "Mingsha JVM");
        assertEquals(12345, process.pid);
        assertEquals("boot", process.type);
        assertEquals("Mingsha JVM", process.name);
    }

    @Test
    void testJvmProcessToString() {
        com.mingsha.jvm.tools.jps.JpsTool.JvmProcess process = 
            new com.mingsha.jvm.tools.jps.JpsTool.JvmProcess(12345, "boot", "Mingsha JVM");
        assertNotNull(process.toString());
    }
}