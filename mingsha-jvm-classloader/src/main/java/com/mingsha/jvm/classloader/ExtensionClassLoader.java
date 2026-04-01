package com.mingsha.jvm.classloader;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Extension ClassLoader - loads classes from extension directories.
 */
public class ExtensionClassLoader extends MingshaClassLoader {
    private final List<Path> extPaths;

    public ExtensionClassLoader(BootstrapClassLoader parent) {
        super(parent);
        this.extPaths = new ArrayList<>();
        String extDir = System.getProperty("java.ext.dirs", System.getProperty("java.home", "./lib") + "/lib/ext");
        File dir = new File(extDir);
        if (dir.exists() && dir.isDirectory()) {
            File[] jars = dir.listFiles((d, n) -> n.endsWith(".jar"));
            if (jars != null) {
                for (File jar : jars) extPaths.add(jar.toPath());
            }
        }
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        String path = className.replace('.', '/') + ".class";
        for (Path extPath : extPaths) {
            Path classPath = extPath.resolve(path);
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
