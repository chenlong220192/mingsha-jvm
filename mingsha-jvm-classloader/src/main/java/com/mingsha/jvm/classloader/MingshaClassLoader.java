package com.mingsha.jvm.classloader;

import java.io.File;

/**
 * Abstract base class for all class loaders.
 * Implements parent delegation model.
 */
public abstract class MingshaClassLoader {
    protected final MingshaClassLoader parent;
    protected final String name;

    protected MingshaClassLoader(MingshaClassLoader parent) {
        this.parent = parent;
        this.name = getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }

    protected MingshaClassLoader() {
        this.parent = null;
        this.name = getClass().getSimpleName();
    }

    public String getName() { return name; }
    public MingshaClassLoader getParent() { return parent; }

    public Class<?> loadClass(String className) throws ClassNotFoundException {
        Class<?> clazz = findLoadedClass(className);
        if (clazz != null) return clazz;
        if (parent != null) {
            try { return parent.loadClass(className); } catch (ClassNotFoundException e) { }
        }
        return findClass(className);
    }

    protected Class<?> findLoadedClass(String className) { return null; }

    protected abstract Class<?> findClass(String className) throws ClassNotFoundException;

    protected String classNameToPath(String className) {
        return className.replace('.', File.separatorChar) + ".class";
    }
}
