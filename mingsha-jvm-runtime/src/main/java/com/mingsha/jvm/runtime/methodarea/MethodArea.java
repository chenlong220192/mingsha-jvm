package com.mingsha.jvm.runtime.methodarea;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Method area - stores class metadata.
 * <p>
 * Shared among all threads. Contains:
 * <ul>
 *   <li>Class structures</li>
 *   <li>Method bytecode</li>
 *   <li>Constant pools</li>
 * </ul>
 *
 * @version 1.0.0
 */
public class MethodArea {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(MethodArea.class);

    /** Class storage */
    private final Map<String, KlassModel> klassMap = new ConcurrentHashMap<>();

    /** Adds a class to method area */
    public void addKlass(KlassModel klass) {
        klassMap.put(klass.getName(), klass);
        logger.debug("Class loaded: {}", klass.getName());
    }

    /** Gets a class by name */
    public KlassModel getKlass(String name) {
        return klassMap.get(name);
    }

    /** Checks if class is loaded */
    public boolean isKlassLoaded(String name) {
        return klassMap.containsKey(name);
    }

    /** Removes a class (for class unloading) */
    public void removeKlass(String name) {
        klassMap.remove(name);
        logger.debug("Class unloaded: {}", name);
    }

    /** @return number of loaded classes */
    public int getKlassCount() { return klassMap.size(); }

    /** Clears method area */
    public void clear() {
        klassMap.clear();
        logger.info("MethodArea cleared");
    }
}
