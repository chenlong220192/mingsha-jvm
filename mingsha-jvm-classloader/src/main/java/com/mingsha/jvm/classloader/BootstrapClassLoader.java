package com.mingsha.jvm.classloader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Bootstrap ClassLoader - loads core JDK classes.
 */
public class BootstrapClassLoader extends MingshaClassLoader {
    private final List<Path> bootClassPath;

    public BootstrapClassLoader() {
        super(null);
        this.bootClassPath = new ArrayList<>();
        String javaHome = System.getProperty("java.home", "./lib");
        File rtJar = new File(javaHome + "/lib/rt.jar");
        if (rtJar.exists()) {
            bootClassPath.add(rtJar.toPath());
        }
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        String path = className.replace('.', '/') + ".class";
        for (Path basePath : bootClassPath) {
            Path classPath = basePath.resolve(path);
            if (Files.exists(classPath)) {
                try {
                    byte[] bytecode = Files.readAllBytes(classPath);
                    return defineClass(className, bytecode, 0, bytecode.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(className);
                }
            }
        }
        throw new ClassNotFoundException(className);
    }

    protected native Class<?> defineClass(String name, byte[] b, int off, int len) throws ClassFormatError;
}
