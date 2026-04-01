package com.mingsha.jvm.classloader;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Application ClassLoader - loads classes from application classpath.
 */
public class AppClassLoader extends MingshaClassLoader {
    private final List<Path> classPath;

    public AppClassLoader(ExtensionClassLoader parent) {
        super(parent);
        this.classPath = new ArrayList<>();
        String cp = System.getProperty("java.class.path", ".");
        for (String entry : cp.split(File.pathSeparator)) {
            File f = new File(entry);
            if (f.exists()) classPath.add(f.toPath());
        }
    }

    public AppClassLoader() {
        this(new ExtensionClassLoader(new BootstrapClassLoader()));
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        String path = className.replace('.', '/') + ".class";
        for (Path cpEntry : classPath) {
            Path classPath = cpEntry.resolve(path);
            if (Files.exists(classPath)) {
                try {
                    byte[] bytecode = Files.readAllBytes(classPath);
                    return defineClass(className, bytecode, 0, bytecode.length);
                } catch (Exception e) {
                    throw new ClassNotFoundException(className);
                }
            }
        }
        throw new ClassNotFoundException(className);
    }

    protected native Class<?> defineClass(String name, byte[] b, int off, int len) throws ClassFormatError;
}
