package com.mingsha.jvm.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.io.File;

/**
 * Mingsha JVM system properties holder.
 * <p>
 * This class provides access to JVM-level system properties, similar to
 * {@code java.lang.System#getProperties()}. It initializes the standard
 * Java system properties that would normally be set by the JVM.
 *
 * @version 1.0.0
 * @author Mingsha JVM Team
 */
public final class MingshaVMProperties {

    /** Logger instance for this class */
    private static final Logger logger = LoggerFactory.getLogger(MingshaVMProperties.class);

    /** Singleton instance */
    private static final MingshaVMProperties INSTANCE = new MingshaVMProperties();

    /** Internal properties storage */
    private final Properties properties;

    /**
     * Private constructor to enforce singleton pattern.
     * Initializes all standard Java system properties.
     */
    private MingshaVMProperties() {
        this.properties = new Properties();
        initializeProperties();
        logger.debug("MingshaVMProperties initialized with {} properties", properties.size());
    }

    /**
     * Initializes all standard Java system properties.
     */
    private void initializeProperties() {
        // Java version properties
        properties.setProperty("java.version", MingshaVMVersion.getJavaVersion());
        properties.setProperty("java.version.date", "2021-10-19");
        properties.setProperty("java.vendor", "Mingsha");
        properties.setProperty("java.vendor.url", "https://github.com/chenlong220192/mingsha-jvm");
        properties.setProperty("java.vendor.url.bug", "https://github.com/chenlong220192/mingsha-jvm/issues");

        // JVM properties
        properties.setProperty("java.vm.name", MingshaVMVersion.getJVMName());
        properties.setProperty("java.vm.version", MingshaVMVersion.getJVMVersion());
        properties.setProperty("java.vm.info", "mixed mode");
        properties.setProperty("java.vm.specification.name", "Java Virtual Machine Specification");
        properties.setProperty("java.vm.specification.version", MingshaVMVersion.getSpecificationVersion());
        properties.setProperty("java.vm.specification.vendor", "Oracle Corporation");

        // Java specification properties
        properties.setProperty("java.specification.name", "Java Platform API Specification");
        properties.setProperty("java.specification.version", MingshaVMVersion.getSpecificationVersion());
        properties.setProperty("java.specification.vendor", "Oracle Corporation");

        // Class file version (Java 17 = 61)
        properties.setProperty("java.class.version", "61.0");

        // Class path
        properties.setProperty("java.class.path", System.getProperty("java.class.path", "./lib"));

        // Library path
        properties.setProperty("java.library.path", System.getProperty("java.library.path", "./lib/native"));

        // IO temporary directory
        properties.setProperty("java.io.tmpdir", System.getProperty("java.io.tmpdir", "/tmp"));

        // Compiler (empty for JIT-only JVM)
        properties.setProperty("java.compiler", "");

        // OS properties
        properties.setProperty("os.name", System.getProperty("os.name", "Mac OS X"));
        properties.setProperty("os.arch", System.getProperty("os.arch", "x86_64"));
        properties.setProperty("os.version", System.getProperty("os.version", "26.4"));

        // Path separator
        properties.setProperty("path.separator", File.pathSeparator);

        // File separator
        properties.setProperty("file.separator", File.separator);

        // Line separator
        properties.setProperty("line.separator", System.lineSeparator());

        // User properties
        properties.setProperty("user.name", System.getProperty("user.name", "user"));
        properties.setProperty("user.home", System.getProperty("user.home", System.getProperty("user.home", "/home/user")));
        properties.setProperty("user.dir", System.getProperty("user.dir", "."));
        properties.setProperty("user.country", System.getProperty("user.country", "CN"));
        properties.setProperty("user.language", System.getProperty("user.language", "zh"));

        // Architecture properties
        properties.setProperty("sun.arch.data.model", "64");
        properties.setProperty("sun.cpu.endian", "little");
        properties.setProperty("sun.io.unicode.encoding", "UnicodeBig");
        properties.setProperty("sun.management.compiler", "HotSpot Tiered Compilers");

        logger.debug("System properties initialized successfully");
    }

    /**
     * Returns the singleton instance of MingshaVMProperties.
     *
     * @return the singleton instance
     */
    public static MingshaVMProperties getInstance() {
        return INSTANCE;
    }

    /**
     * Gets a system property by key.
     *
     * @param key the property key
     * @return the property value, or null if not found
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Gets a system property by key with a default value.
     *
     * @param key the property key
     * @param defaultValue the default value if key not found
     * @return the property value, or defaultValue if not found
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Sets a system property.
     *
     * @param key the property key
     * @param value the property value
     */
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        logger.debug("Set property: {} = {}", key, value);
    }

    /**
     * Returns all properties as a Properties object.
     *
     * @return a copy of the properties
     */
    public Properties toProperties() {
        return (Properties) properties.clone();
    }

    /**
     * Returns the number of properties.
     *
     * @return the property count
     */
    public int size() {
        return properties.size();
    }

    @Override
    public String toString() {
        return "MingshaVMProperties{" +
                "propertyCount=" + properties.size() +
                '}';
    }
}
