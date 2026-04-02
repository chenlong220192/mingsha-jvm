package com.mingsha.jvm.runtime.methodarea;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mingsha.jvm.classloader.BootstrapClassLoader;

public class MethodArea {

    private static final Logger logger = LoggerFactory.getLogger(MethodArea.class);

    private static final MethodArea INSTANCE = new MethodArea();

    private final Map<String, KlassModel> klassMap = new ConcurrentHashMap<>();
    private BootstrapClassLoader classLoader;

    private MethodArea() {}

    public static MethodArea getInstance() {
        return INSTANCE;
    }

    public void setClassLoader(BootstrapClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public BootstrapClassLoader getClassLoader() {
        return classLoader;
    }

    public void addKlass(KlassModel klass) {
        klassMap.put(klass.getName(), klass);
        logger.debug("Class loaded: {}", klass.getName());
    }

    public KlassModel getKlass(String name) {
        return klassMap.get(name);
    }

    public boolean isKlassLoaded(String name) {
        return klassMap.containsKey(name);
    }

    public void removeKlass(String name) {
        klassMap.remove(name);
        logger.debug("Class unloaded: {}", name);
    }

    public int getKlassCount() { return klassMap.size(); }

    public void clear() {
        klassMap.clear();
        logger.info("MethodArea cleared");
    }
}
