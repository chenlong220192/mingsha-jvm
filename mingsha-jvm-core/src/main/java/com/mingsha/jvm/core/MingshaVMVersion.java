package com.mingsha.jvm.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mingsha JVM version information holder.
 * <p>
 * This class provides version details about the Mingsha JVM implementation,
 * including version strings compatible with the Java version reported by the JVM.
 *
 * @version 1.0.0
 * @author Mingsha JVM Team
 */
public final class MingshaVMVersion {

    /** Logger instance for this class */
    private static final Logger logger = LoggerFactory.getLogger(MingshaVMVersion.class);

    /** Mingsha JVM version string (e.g., "1.0.0-SNAPSHOT") */
    public static final String VERSION = "1.0.0-SNAPSHOT";

    /** Java version this JVM targets (e.g., "17.0.1") */
    public static final String JAVA_VERSION = "17.0.1";

    /** Mingsha JVM implementation name */
    public static final String JVM_NAME = "Mingsha JVM";

    /** Mingsha JVM version details string */
    public static final String JVM_VERSION = "17.0.1+12-39";

    /** Build information timestamp */
    public static final String BUILD_DATE = "2026-04-02";

    /** JVM specification version */
    public static final String SPECIFICATION_VERSION = "17";

    /**
     * Private constructor to prevent instantiation.
     * This class contains only static methods and constants.
     */
    private MingshaVMVersion() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    /**
     * Returns the Mingsha JVM version string.
     *
     * @return the version string (e.g., "1.0.0-SNAPSHOT")
     */
    public static String getVersion() {
        return VERSION;
    }

    /**
     * Returns the Java version this JVM targets.
     *
     * @return the Java version string (e.g., "17.0.1")
     */
    public static String getJavaVersion() {
        return JAVA_VERSION;
    }

    /**
     * Returns the Mingsha JVM implementation name.
     *
     * @return the JVM name (e.g., "Mingsha JVM")
     */
    public static String getJVMName() {
        return JVM_NAME;
    }

    /**
     * Returns the Mingsha JVM version details.
     *
     * @return the full version string (e.g., "17.0.1+12-39")
     */
    public static String getJVMVersion() {
        return JVM_VERSION;
    }

    /**
     * Returns the JVM specification version.
     *
     * @return the specification version (e.g., "17")
     */
    public static String getSpecificationVersion() {
        return SPECIFICATION_VERSION;
    }

    /**
     * Returns the build date of this JVM implementation.
     *
     * @return the build date string
     */
    public static String getBuildDate() {
        return BUILD_DATE;
    }

    /**
     * Returns the full version information string.
     * <p>
     * Format:
     * <pre>
     * Mingsha JVM version 1.0.0-SNAPSHOT
     * OpenJDK Runtime Environment (build 17.0.1+12-39)
     * OpenJDK 64-Bit Server VM (build 17.0.1+12-39, mixed mode, sharing)
     * </pre>
     *
     * @return the full version information string
     */
    public static String getFullVersion() {
        return String.format(
            "Mingsha JVM version %s\n" +
            "OpenJDK Runtime Environment (build %s)\n" +
            "OpenJDK 64-Bit Server VM (build %s, mixed mode, sharing)",
            VERSION, JVM_VERSION, JVM_VERSION
        );
    }

    /**
     * Main entry point - displays version information.
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        logger.info("Displaying Mingsha JVM version information");
        System.out.println(getFullVersion());
    }
}
