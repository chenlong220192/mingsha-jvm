package com.mingsha.jvm.runtime.methodarea;

import java.util.HashMap;
import java.util.Map;

/**
 * Class metadata stored in method area.
 */
public class KlassModel {
    private final String name;
    private final String superClassName;
    private final int accessFlags;
    private final Map<String, MethodInfo> methods = new HashMap<>();
    private final Map<String, FieldInfo> fields = new HashMap<>();

    public KlassModel(String name, String superClassName, int accessFlags) {
        this.name = name;
        this.superClassName = superClassName;
        this.accessFlags = accessFlags;
    }

    public String getName() { return name; }
    public String getSuperClassName() { return superClassName; }
    public int getAccessFlags() { return accessFlags; }
    public Map<String, MethodInfo> getMethods() { return methods; }
    public Map<String, FieldInfo> getFields() { return fields; }

    public void addMethod(MethodInfo method) { methods.put(method.name + method.descriptor, method); }
    public void addField(FieldInfo field) { fields.put(field.name + field.descriptor, field); }

    public static class MethodInfo {
        public final String name;
        public final String descriptor;
        public final int accessFlags;
        public byte[] bytecode;
        public int maxStack;
        public int maxLocals;

        public MethodInfo(String name, String descriptor, int accessFlags) {
            this.name = name;
            this.descriptor = descriptor;
            this.accessFlags = accessFlags;
        }
    }

    public static class FieldInfo {
        public final String name;
        public final String descriptor;
        public final int accessFlags;

        public FieldInfo(String name, String descriptor, int accessFlags) {
            this.name = name;
            this.descriptor = descriptor;
            this.accessFlags = accessFlags;
        }
    }
}
