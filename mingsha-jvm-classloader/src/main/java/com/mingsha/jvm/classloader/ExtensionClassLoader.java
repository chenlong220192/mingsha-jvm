package com.mingsha.jvm.classloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Extension ClassLoader - loads classes from extension directories.
 * <p>
 * Child of BootstrapClassLoader, parent of AppClassLoader.
 *
 * @version 1.0.0
 * @see MingshaClassLoader
 */
public class ExtensionClassLoader extends MingshaClassLoader {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(ExtensionClassLoader.class);

    /** Extension classpath entries */
    private final List<Path> extPaths;

    /** Class cache */
    private final List<Class<?>> loadedClasses = new ArrayList<>();

    /**
     * Constructor with parent.
     *
     * @param parent bootstrap class loader
     */
    public ExtensionClassLoader(BootstrapClassLoader parent) {
        super(parent);
        this.extPaths = new ArrayList<>();
        initializeExtPaths();
        logger.info("ExtensionClassLoader initialized with {} entries", extPaths.size());
    }

    /**
     * Initializes extension paths.
     */
    private void initializeExtPaths() {
        String extDirs = System.getProperty("java.ext.dirs",
                System.getProperty("java.home", "./lib") + "/lib/ext");

        File dir = new File(extDirs);
        if (dir.exists() && dir.isDirectory()) {
            File[] jars = dir.listFiles((d, n) -> n.endsWith(".jar"));
            if (jars != null) {
                for (File jar : jars) {
                    extPaths.add(jar.toPath());
                    logger.debug("Added ext path: {}", jar);
                }
            }
        }
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        logger.debug("ExtensionClassLoader.findClass: {}", className);

        String path = className.replace('.', '/') + ".class";

        for (Path extPath : extPaths) {
            Path classPath = extPath.resolve(path);
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

        throw new ClassNotFoundException("ExtensionClassLoader cannot find class: " + className);
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
     *
     * @param name class name
     * @param bytecode class bytecode
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
