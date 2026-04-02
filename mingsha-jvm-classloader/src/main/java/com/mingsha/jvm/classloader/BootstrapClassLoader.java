package com.mingsha.jvm.classloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Bootstrap ClassLoader - loads core JDK classes.
 * <p>
 * This is the root class loader in the parent delegation chain.
 *
 * @version 1.0.0
 * @see MingshaClassLoader
 */
public class BootstrapClassLoader extends MingshaClassLoader {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(BootstrapClassLoader.class);

    /** Bootstrap classpath entries */
    private final List<Path> bootClassPath;

    /** Class cache */
    private final List<Class<?>> loadedClasses = new ArrayList<>();

    /**
     * Constructor - initializes bootstrap classpath.
     */
    public BootstrapClassLoader() {
        super(null);
        this.bootClassPath = new ArrayList<>();
        initializeBootstrapClassPath();
        logger.info("BootstrapClassLoader initialized with {} entries", bootClassPath.size());
    }

    /**
     * Initializes bootstrap classpath entries.
     */
    private void initializeBootstrapClassPath() {
        String javaHome = System.getProperty("java.home", "./lib");
        String[] jars = {"rt.jar", "resources.jar", "jsse.jar", "jce.jar", "charsets.jar"};

        for (String jar : jars) {
            Path jarPath = Path.of(javaHome, "lib", jar);
            if (Files.exists(jarPath)) {
                bootClassPath.add(jarPath);
                logger.debug("Added bootstrap entry: {}", jarPath);
            }
        }

        // Also check classes directory
        Path classesPath = Path.of(javaHome, "lib", "classes");
        if (Files.exists(classesPath)) {
            bootClassPath.add(classesPath);
            logger.debug("Added bootstrap entry: {}", classesPath);
        }
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        logger.debug("BootstrapClassLoader.findClass: {}", className);

        String path = className.replace('.', '/') + ".class";

        for (Path basePath : bootClassPath) {
            Path classPath = basePath.resolve(path);
            if (Files.exists(classPath)) {
                try {
                    byte[] bytecode = Files.readAllBytes(classPath);
                    Class<?> clazz = defineClassImpl(className, bytecode);
                    loadedClasses.add(clazz);
                    logger.info("Loaded class: {} from {}", className, classPath);
                    return clazz;
                } catch (Exception e) {
                    logger.warn("Failed to load class: {} from {}", className, classPath);
                }
            }
        }

        throw new ClassNotFoundException("BootstrapClassLoader cannot find class: " + className);
    }

    @Override
    protected Class<?> findLoadedClass(String className) {
        for (Class<?> clazz : loadedClasses) {
            if (clazz.getName().equals(className)) {
                return clazz;
            }
        }
        return null;
    }

    /**
     * Defines a class from bytecode.
     * <p>
     * Simulation - uses Class.forName as placeholder.
     *
     * @param name class name
     * @param bytecode class bytecode (unused)
     * @return defined Class
     */
    private Class<?> defineClassImpl(String name, byte[] bytecode) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to define class: " + name, e);
        }
    }
}
