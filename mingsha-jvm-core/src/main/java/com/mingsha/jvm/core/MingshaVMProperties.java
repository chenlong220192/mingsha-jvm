package com.mingsha.jvm.core;

import java.util.Properties;

/**
 * Mingsha JVM system properties holder.
 */
public final class MingshaVMProperties {
    private static final MingshaVMProperties INSTANCE = new MingshaVMProperties();
    private final Properties properties = new Properties();

    private MingshaVMProperties() {
        properties.setProperty("java.version", "17.0.1");
        properties.setProperty("java.version.date", "2021-10-19");
        properties.setProperty("java.vendor", "Mingsha");
        properties.setProperty("java.vendor.url", "https://github.com/chenlong220192/mingsha-jvm");
        properties.setProperty("java.vm.name", "Mingsha JVM");
        properties.setProperty("java.vm.version", MingshaVMVersion.getJVMVersion());
        properties.setProperty("java.vm.info", "mixed mode");
        properties.setProperty("java.specification.version", "17");
        properties.setProperty("java.class.version", "61.0");
        properties.setProperty("os.name", System.getProperty("os.name", "Mac OS X"));
        properties.setProperty("os.arch", System.getProperty("os.arch", "x86_64"));
        properties.setProperty("os.version", System.getProperty("os.version", "26.4"));
    }

    public static MingshaVMProperties getInstance() { return INSTANCE; }
    public String getProperty(String key) { return properties.getProperty(key); }
    public String getProperty(String key, String defaultValue) { return properties.getProperty(key, defaultValue); }
    public void setProperty(String key, String value) { properties.setProperty(key, value); }
}
