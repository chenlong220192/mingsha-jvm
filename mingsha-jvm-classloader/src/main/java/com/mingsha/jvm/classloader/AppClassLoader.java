package com.mingsha.jvm.classloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Application ClassLoader - loads classes from application classpath.
 * <p>
 * This is the class loader that loads the application's own classes.
 *
 * @version 1.0.0
 * @see MingshaClassLoader
 */
public class AppClassLoader extends MingshaClassLoader {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(AppClassLoader.class);

    /** Application classpath entries */
    private final List<Path> classPath;

    /** Class cache */
    private final List<Class<?>> loadedClasses = new ArrayList<>();

    /**
     * Constructor with parent.
     *
     * @param parent extension class loader
     */
    public AppClassLoader(ExtensionClassLoader parent) {
        super(parent);
        this.classPath = new ArrayList<>();
        initializeClassPath();
        logger.info("AppClassLoader initialized with {} entries", classPath.size());
    }

    /** Default constructor */
    public AppClassLoader() {
        this(new ExtensionClassLoader(new BootstrapClassLoader()));
    }

    /**
     * Initializes classpath from java.class.path property.
     */
    private void initializeClassPath() {
        String cp = System.getProperty("java.class.path", ".");
        for (String entry : cp.split(File.pathSeparator)) {
            File f = new File(entry);
            if (f.exists()) {
                classPath.add(f.toPath());
                logger.debug("Added classpath entry: {}", f);
            }
        }
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        logger.debug("AppClassLoader.findClass: {}", className);

        String path = className.replace('.', '/') + ".class";

        for (Path cpEntry : classPath) {
            Path classFile = cpEntry.resolve(path);
            if (Files.exists(classFile)) {
                try {
                    byte[] bytecode = Files.readAllBytes(classFile);
                    Class<?> clazz = defineClassImpl(className, bytecode);
                    loadedClasses.add(clazz);
                    logger.info("Loaded class: {} from {}", className, classFile);
                    return clazz;
                } catch (Exception e) {
                    logger.warn("Failed to load class: {} from {}", className, classFile);
                }
            }
        }

        throw new ClassNotFoundException("AppClassLoader cannot find class: " + className);
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
     * This is a simulation - uses Class.forName as placeholder.
     * Real implementation would use native defineClass.
     *
     * @param name class name
     * @param bytecode class bytecode (unused in simulation)
     * @return defined Class
     */
    private Class<?> defineClassImpl(String name, byte[] bytecode) {
        // Simulation: just use Class.forName to load the class
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to define class: " + name, e);
        }
    }
}
