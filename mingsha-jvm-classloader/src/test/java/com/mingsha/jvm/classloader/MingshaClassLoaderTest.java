package com.mingsha.jvm.classloader;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MingshaClassLoaderTest {

    @Test
    void testMingshaClassLoaderCreation() {
        MingshaClassLoader loader = new TestableClassLoader(null);
        assertNotNull(loader);
        assertNotNull(loader.getName());
        assertNull(loader.getParent());
    }

    @Test
    void testMingshaClassLoaderWithParent() {
        MingshaClassLoader parent = new TestableClassLoader(null);
        MingshaClassLoader child = new TestableClassLoader(parent);
        assertNotNull(child.getParent());
        assertEquals(parent, child.getParent());
    }

    @Test
    void testClassNameToPath() {
        MingshaClassLoader loader = new TestableClassLoader(null);
        String path = loader.classNameToPath("java.lang.Object");
        assertTrue(path.contains("java/lang/Object.class"));
    }

    @Test
    void testLoadClassNotFound() {
        MingshaClassLoader loader = new TestableClassLoader(null);
        assertThrows(ClassNotFoundException.class, () -> loader.loadClass("nonexistent.Class"));
    }

    @Test
    void testParentDelegationToFindLoadedClass() {
        TestableClassLoader parent = new TestableClassLoader(null);
        parent.setPreLoadedClass("java.lang.Object");
        TestableClassLoader child = new TestableClassLoader(parent);
        assertDoesNotThrow(() -> {
            Class<?> clazz = child.loadClass("java.lang.Object");
            assertNotNull(clazz);
        });
    }

    @Test
    void testFindLoadedClassReturnsNull() {
        TestableClassLoader loader = new TestableClassLoader(null);
        loader.setPreLoadedClass("loaded.Class");
        assertNull(loader.findLoadedClass("notloaded.Class"));
    }

    @Test
    void testLoadClassDelegatesToParent() {
        TestableClassLoader parent = new TestableClassLoader(null);
        parent.setPreLoadedClass("java.lang.Object");
        TestableClassLoader child = new TestableClassLoader(parent);
        assertDoesNotThrow(() -> {
            Class<?> clazz = child.loadClass("java.lang.Object");
            assertNotNull(clazz);
        });
    }

    private static class TestableClassLoader extends MingshaClassLoader {
        private String preLoadedClass;

        TestableClassLoader(MingshaClassLoader parent) {
            super(parent);
        }

        void setPreLoadedClass(String className) {
            this.preLoadedClass = className;
        }

        @Override
        protected Class<?> findClass(String className) throws ClassNotFoundException {
            if (preLoadedClass != null && preLoadedClass.equals(className)) {
                try {
                    return Class.forName(className);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            throw new ClassNotFoundException("Test class not found: " + className);
        }

        @Override
        protected Class<?> findLoadedClass(String className) {
            if (preLoadedClass != null && preLoadedClass.equals(className)) {
                try {
                    return Class.forName(className);
                } catch (ClassNotFoundException e) {
                    return null;
                }
            }
            return null;
        }
    }
}