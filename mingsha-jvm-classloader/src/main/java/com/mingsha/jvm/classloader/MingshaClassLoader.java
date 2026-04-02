package com.mingsha.jvm.classloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;

/**
 * Abstract base class for all class loaders.
 * <p>
 * Implements the parent delegation model as specified in JVM specification.
 * Class loaders are consulted in the following order:
 * <ol>
 *   <li>Check if class is already loaded (cached)</li>
 *   <li>Ask parent class loader to load (if not bootstrap)</li>
 *   <li>Load class using this class loader's {@link #findClass(String)}</li>
 * </ol>
 *
 * @version 1.0.0
 * @see BootstrapClassLoader
 * @see ExtensionClassLoader
 * @see AppClassLoader
 */
public abstract class MingshaClassLoader {

    /** Logger instance */
    protected static final Logger logger = LoggerFactory.getLogger(MingshaClassLoader.class);

    /** Parent class loader (null for bootstrap) */
    protected final MingshaClassLoader parent;

    /** This class loader's name for debugging */
    protected final String name;

    /**
     * Constructor with parent.
     *
     * @param parent the parent class loader (null for bootstrap)
     */
    protected MingshaClassLoader(MingshaClassLoader parent) {
        this.parent = parent;
        this.name = getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
        logger.debug("ClassLoader created: {} with parent: {}", name, parent);
    }

    /** Default constructor (bootstrap class loader) */
    protected MingshaClassLoader() {
        this.parent = null;
        this.name = getClass().getSimpleName();
        logger.debug("Bootstrap ClassLoader created");
    }

    /** @return class loader name */
    public String getName() { return name; }

    /** @return parent class loader (null for bootstrap) */
    public MingshaClassLoader getParent() { return parent; }

    /**
     * Loads the class with the given name.
     * <p>
     * Uses parent delegation model:
     * <ol>
     *   <li>Check cache (findLoadedClass)</li>
     *   <li>Delegate to parent if exists</li>
     *   <li>Load using findClass</li>
     * </ol>
     *
     * @param className fully qualified class name (e.g., java.lang.Object)
     * @return loaded Class object
     * @throws ClassNotFoundException if class cannot be found
     */
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        logger.debug("loadClass called: {}", className);

        // Step 1: Check if already loaded
        Class<?> clazz = findLoadedClass(className);
        if (clazz != null) {
            logger.debug("Class {} found in cache", className);
            return clazz;
        }

        // Step 2: Delegate to parent
        if (parent != null) {
            try {
                clazz = parent.loadClass(className);
                logger.debug("Class {} loaded by parent: {}", className, parent.getName());
                return clazz;
            } catch (ClassNotFoundException e) {
                logger.debug("Parent {} cannot load {}, trying own load", parent.getName(), className);
            }
        }

        // Step 3: Load using this class loader
        clazz = findClass(className);
        logger.info("Class {} loaded by: {}", className, name);
        return clazz;
    }

    /**
     * Checks if class is already loaded in this class loader's cache.
     * <p>
     * Subclasses should override to provide caching.
     *
     * @param className the class name
     * @return the Class if cached, null otherwise
     */
    protected Class<?> findLoadedClass(String className) {
        return null;
    }

    /**
     * Finds the class with the given name.
     * <p>
     * Subclasses must implement this to provide class loading logic.
     *
     * @param className the class name
     * @return the loaded Class
     * @throws ClassNotFoundException if not found
     */
    protected abstract Class<?> findClass(String className) throws ClassNotFoundException;

    /**
     * Converts class name to file path.
     * <p>
     * Example: java.lang.Object -> java/lang/Object.class
     *
     * @param className fully qualified class name
     * @return file path relative to class path root
     */
    protected String classNameToPath(String className) {
        return className.replace('.', File.separatorChar) + ".class";
    }
}
