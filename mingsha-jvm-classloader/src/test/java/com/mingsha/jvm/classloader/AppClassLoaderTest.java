package com.mingsha.jvm.classloader;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppClassLoaderTest {

    @Test
    void testAppClassLoaderCreation() {
        AppClassLoader loader = new AppClassLoader();
        assertNotNull(loader);
    }

    @Test
    void testAppClassLoaderWithParent() {
        BootstrapClassLoader bootstrap = new BootstrapClassLoader();
        ExtensionClassLoader extension = new ExtensionClassLoader(bootstrap);
        AppClassLoader loader = new AppClassLoader(extension);
        assertNotNull(loader);
        assertEquals(extension, loader.getParent());
    }

    @Test
    void testFindLoadedClassReturnsNullForUnloaded() {
        AppClassLoader loader = new AppClassLoader();
        assertNull(loader.findLoadedClass("unloaded.Class"));
    }

    @Test
    void testLoadNonExistentClass() {
        AppClassLoader loader = new AppClassLoader();
        assertThrows(ClassNotFoundException.class, () -> loader.loadClass("nonexistent.AppClass"));
    }
}