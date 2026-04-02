package com.mingsha.jvm.runtime.methodarea;

import java.util.Map;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class metadata stored in method area.
 * <p>
 * Represents a loaded class in the JVM runtime.
 *
 * @version 1.0.0
 */
public class KlassModel {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(KlassModel.class);

    /** Fully qualified class name (e.g., java/lang/Object) */
    private final String name;

    /** Superclass name (e.g., java/lang/Object) */
    private final String superClassName;

    /** Access flags (public, final, abstract, etc.) */
    private final int accessFlags;

    /** Methods map: name+descriptor -> MethodInfo */
    private final Map<String, MethodInfo> methods = new HashMap<>();

    /** Fields map: name+descriptor -> FieldInfo */
    private final Map<String, FieldInfo> fields = new HashMap<>();

    /**
     * Constructor.
     *
     * @param name fully qualified class name
     * @param superClassName superclass name
     * @param accessFlags access flags
     */
    public KlassModel(String name, String superClassName, int accessFlags) {
        this.name = name;
        this.superClassName = superClassName;
        this.accessFlags = accessFlags;
        logger.debug("Created KlassModel: {}", name);
    }

    /** @return class name */
    public String getName() { return name; }

    /** @return superclass name */
    public String getSuperClassName() { return superClassName; }

    /** @return access flags */
    public int getAccessFlags() { return accessFlags; }

    /** @return methods map */
    public Map<String, MethodInfo> getMethods() { return methods; }

    /** @return fields map */
    public Map<String, FieldInfo> getFields() { return fields; }

    /**
     * Adds a method to this class.
     *
     * @param method method to add
     */
    public void addMethod(MethodInfo method) {
        methods.put(method.name + method.descriptor, method);
        logger.trace("Added method: {}:{}", method.name, method.descriptor);
    }

    /**
     * Adds a field to this class.
     *
     * @param field field to add
     */
    public void addField(FieldInfo field) {
        fields.put(field.name + field.descriptor, field);
        logger.trace("Added field: {}:{}", field.name, field.descriptor);
    }

    /**
     * Method metadata.
     */
    public static class MethodInfo {
        /** Method name (e.g., main, <init>) */
        public final String name;

        /** Method descriptor (e.g., ([Ljava/lang/String;)V) */
        public final String descriptor;

        /** Access flags */
        public final int accessFlags;

        /** Method bytecode */
        public byte[] bytecode;

        /** Maximum stack size */
        public int maxStack;

        /** Maximum local variables */
        public int maxLocals;

        /**
         * Constructor.
         *
         * @param name method name
         * @param descriptor method descriptor
         * @param accessFlags access flags
         */
        public MethodInfo(String name, String descriptor, int accessFlags) {
            this.name = name;
            this.descriptor = descriptor;
            this.accessFlags = accessFlags;
        }
    }

    /**
     * Field metadata.
     */
    public static class FieldInfo {
        /** Field name */
        public final String name;

        /** Field descriptor */
        public final String descriptor;

        /** Access flags */
        public final int accessFlags;

        /**
         * Constructor.
         *
         * @param name field name
         * @param descriptor field descriptor
         * @param accessFlags access flags
         */
        public FieldInfo(String name, String descriptor, int accessFlags) {
            this.name = name;
            this.descriptor = descriptor;
            this.accessFlags = accessFlags;
        }
    }
}
