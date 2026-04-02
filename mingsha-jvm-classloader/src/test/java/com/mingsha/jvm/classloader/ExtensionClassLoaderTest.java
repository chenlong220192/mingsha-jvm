package com.mingsha.jvm.classloader;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExtensionClassLoaderTest {

    @Test
    void testExtensionClassLoaderCreation() {
        BootstrapClassLoader bootstrap = new BootstrapClassLoader();
        ExtensionClassLoader loader = new ExtensionClassLoader(bootstrap);
        assertNotNull(loader);
        assertEquals(bootstrap, loader.getParent());
    }

    @Test
    void testExtensionClassLoaderName() {
        BootstrapClassLoader bootstrap = new BootstrapClassLoader();
        ExtensionClassLoader loader = new ExtensionClassLoader(bootstrap);
        assertNotNull(loader.getName());
    }

    @Test
    void testFindLoadedClassReturnsNullForUnloaded() {
        BootstrapClassLoader bootstrap = new BootstrapClassLoader();
        ExtensionClassLoader loader = new ExtensionClassLoader(bootstrap);
        assertNull(loader.findLoadedClass("unloaded.Class"));
    }

    @Test
    void testLoadNonExistentClass() {
        BootstrapClassLoader bootstrap = new BootstrapClassLoader();
        ExtensionClassLoader loader = new ExtensionClassLoader(bootstrap);
        assertThrows(ClassNotFoundException.class, () -> loader.loadClass("nonexistent.ExtensionClass"));
    }
}