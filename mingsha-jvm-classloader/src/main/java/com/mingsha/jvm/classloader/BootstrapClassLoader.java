package com.mingsha.jvm.classloader;

import com.mingsha.jvm.core.classfile.ClassFile;
import com.mingsha.jvm.core.classfile.ClassFileParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BootstrapClassLoader extends MingshaClassLoader {

    private static final Logger logger = LoggerFactory.getLogger(BootstrapClassLoader.class);

    private final List<Path> bootClassPath;
    private final Map<String, ClassFile> loadedClasses = new HashMap<>();
    private final ClassFileParser classFileParser = new ClassFileParser();

    public BootstrapClassLoader() {
        super(null);
        this.bootClassPath = new ArrayList<>();
        initializeBootstrapClassPath();
        logger.info("BootstrapClassLoader initialized with {} entries", bootClassPath.size());
    }

    private void initializeBootstrapClassPath() {
        String javaHome = System.getProperty("java.home", "./lib");
        
        Path classesPath = Path.of(javaHome, "lib", "classes");
        if (Files.exists(classesPath)) {
            bootClassPath.add(classesPath);
            logger.debug("Added bootstrap entry: {}", classesPath);
        }

        String[] jars = {"rt.jar", "resources.jar", "jsse.jar", "jce.jar", "charsets.jar"};
        for (String jar : jars) {
            Path jarPath = Path.of(javaHome, "lib", jar);
            if (Files.exists(jarPath)) {
                bootClassPath.add(jarPath);
                logger.debug("Added bootstrap entry: {}", jarPath);
            }
        }
    }

    public ClassFile loadClassFile(String className) throws ClassNotFoundException {
        logger.debug("Loading ClassFile: {}", className);
        
        if (loadedClasses.containsKey(className)) {
            return loadedClasses.get(className);
        }

        String path = className.replace('.', '/') + ".class";

        for (Path basePath : bootClassPath) {
            Path classPath = basePath.resolve(path);
            if (Files.exists(classPath)) {
                try {
                    byte[] bytecode = Files.readAllBytes(classPath);
                    ClassFile classFile = classFileParser.parse(bytecode);
                    loadedClasses.put(className, classFile);
                    logger.info("Loaded ClassFile: {} from {}", className, classPath);
                    return classFile;
                } catch (IOException e) {
                    logger.warn("Failed to load ClassFile: {} from {}", className, classPath);
                }
            }
        }

        throw new ClassNotFoundException("BootstrapClassLoader cannot find ClassFile: " + className);
    }

    public ClassFile findLoadedClassFile(String className) {
        return loadedClasses.get(className);
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        try {
            loadClassFile(className);
            return Object.class;
        } catch (ClassNotFoundException e) {
            throw e;
        }
    }

    @Override
    protected Class<?> findLoadedClass(String className) {
        return loadedClasses.containsKey(className) ? Object.class : null;
    }
}
