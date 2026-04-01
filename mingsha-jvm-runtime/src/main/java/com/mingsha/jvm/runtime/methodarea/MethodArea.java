package com.mingsha.jvm.runtime.methodarea;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Method area - stores class metadata.
 */
public class MethodArea {
    private final Map<String, KlassModel> klassMap = new ConcurrentHashMap<>();

    public void addKlass(KlassModel klass) { klassMap.put(klass.getName(), klass); }
    public KlassModel getKlass(String name) { return klassMap.get(name); }
    public boolean isKlassLoaded(String name) { return klassMap.containsKey(name); }
    public void removeKlass(String name) { klassMap.remove(name); }
    public int getKlassCount() { return klassMap.size(); }
}
