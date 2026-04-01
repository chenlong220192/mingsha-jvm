package com.mingsha.jvm.core;

/**
 * Mingsha JVM version information.
 */
public final class MingshaVMVersion {
    public static final String VERSION = "1.0.0-SNAPSHOT";
    public static final String JAVA_VERSION = "17.0.1";
    public static final String JVM_NAME = "Mingsha JVM";
    public static final String JVM_VERSION = "17.0.1+12-39";

    public static String getVersion() { return VERSION; }
    public static String getJavaVersion() { return JAVA_VERSION; }
    public static String getJVMName() { return JVM_NAME; }
    public static String getJVMVersion() { return JVM_VERSION; }

    public static void main(String[] args) {
        System.out.println("Mingsha JVM version " + VERSION);
        System.out.println("OpenJDK Runtime Environment (build " + JVM_VERSION + ")");
        System.out.println("OpenJDK 64-Bit Server VM (build " + JVM_VERSION + ", mixed mode, sharing)");
    }
}
