package com.mingsha.jvm.classloader;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BootstrapClassLoaderTest {

    @Test
    void testBootstrapClassLoaderCreation() {
        BootstrapClassLoader loader = new BootstrapClassLoader();
        assertNotNull(loader);
        assertNull(loader.getParent());
    }

    @Test
    void testBootstrapClassLoaderName() {
        BootstrapClassLoader loader = new BootstrapClassLoader();
        assertNotNull(loader.getName());
    }

    @Test
    void testFindLoadedClassReturnsNullForUnloaded() {
        BootstrapClassLoader loader = new BootstrapClassLoader();
        assertNull(loader.findLoadedClass("unloaded.Class"));
    }

    @Test
    void testLoadNonExistentClass() {
        BootstrapClassLoader loader = new BootstrapClassLoader();
        assertThrows(ClassNotFoundException.class, () -> loader.loadClass("nonexistent.BootstrapClass"));
    }

    @Test
    void testClassNameToPathConversion() {
        BootstrapClassLoader loader = new BootstrapClassLoader();
        String path = loader.classNameToPath("java.lang.Object");
        assertEquals("java/lang/Object.class", path);
    }
}