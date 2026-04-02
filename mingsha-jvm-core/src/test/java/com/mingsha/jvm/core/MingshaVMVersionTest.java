package com.mingsha.jvm.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for MingshaVMVersion.
 */
class MingshaVMVersionTest {

    @Test
    void testGetVersion() {
        assertEquals("1.0.0-SNAPSHOT", MingshaVMVersion.getVersion());
    }

    @Test
    void testGetJavaVersion() {
        assertEquals("17.0.1", MingshaVMVersion.getJavaVersion());
    }

    @Test
    void testGetJVMName() {
        assertEquals("Mingsha JVM", MingshaVMVersion.getJVMName());
    }

    @Test
    void testGetJVMVersion() {
        assertEquals("17.0.1+12-39", MingshaVMVersion.getJVMVersion());
    }

    @Test
    void testGetSpecificationVersion() {
        assertEquals("17", MingshaVMVersion.getSpecificationVersion());
    }

    @Test
    void testGetBuildDate() {
        assertNotNull(MingshaVMVersion.getBuildDate());
    }

    @Test
    void testGetFullVersion() {
        String fullVersion = MingshaVMVersion.getFullVersion();
        assertNotNull(fullVersion);
        assertTrue(fullVersion.contains("Mingsha JVM"));
    }
}
