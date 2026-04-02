package com.mingsha.jvm.runtime;

import com.mingsha.jvm.runtime.methodarea.KlassModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

public class MethodResolver {

    private static final Logger logger = LoggerFactory.getLogger(MethodResolver.class);

    private final Map<String, KlassModel> loadedClasses;

    public MethodResolver() {
        this.loadedClasses = new java.util.HashMap<>();
    }

    public void registerClass(String name, KlassModel klass) {
        loadedClasses.put(name, klass);
    }

    public KlassModel getLoadedClass(String name) {
        return loadedClasses.get(name);
    }

    public KlassModel.MethodInfo resolveMethod(KlassModel klass, String methodName, String methodDesc) {
        logger.debug("Resolving method: {} {} in class {}", methodName, methodDesc, klass.getName());

        KlassModel current = klass;
        while (current != null) {
            KlassModel.MethodInfo method = current.findMethod(methodName, methodDesc);
            if (method != null) {
                logger.debug("Found method: {} in class {}", methodName, current.getName());
                return method;
            }
            String superName = current.getSuperClassName();
            if (superName == null || superName.isEmpty()) {
                break;
            }
            current = loadedClasses.get(superName);
        }

        logger.warn("Method not found: {} {} in class {}", methodName, methodDesc, klass.getName());
        return null;
    }

    public KlassModel.MethodInfo resolveInterfaceMethod(KlassModel interfaceKlass, String methodName, String methodDesc) {
        logger.debug("Resolving interface method: {} {} in {}", methodName, methodDesc, interfaceKlass.getName());

        for (KlassModel.MethodInfo method : interfaceKlass.getMethods().values()) {
            if (method.name.equals(methodName) && method.descriptor.equals(methodDesc)) {
                return method;
            }
        }

        return null;
    }

    public String resolveMethodName(com.mingsha.jvm.core.classfile.ConstantPool cp, int methodRefIndex) {
        return cp.getMethodName(methodRefIndex);
    }

    public String resolveMethodDescriptor(com.mingsha.jvm.core.classfile.ConstantPool cp, int methodRefIndex) {
        return cp.getMethodDescriptor(methodRefIndex);
    }

    public String resolveMethodClassName(com.mingsha.jvm.core.classfile.ConstantPool cp, int methodRefIndex) {
        return cp.getMethodClassName(methodRefIndex);
    }
}
