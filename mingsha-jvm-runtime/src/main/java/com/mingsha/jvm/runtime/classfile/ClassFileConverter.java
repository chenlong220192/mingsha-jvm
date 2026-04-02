package com.mingsha.jvm.runtime.classfile;

import com.mingsha.jvm.core.classfile.ClassFile;
import com.mingsha.jvm.core.classfile.ConstantPool;
import com.mingsha.jvm.runtime.methodarea.KlassModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class ClassFileConverter {

    private static final Logger logger = LoggerFactory.getLogger(ClassFileConverter.class);

    public KlassModel convert(ClassFile classFile) {
        logger.debug("Converting ClassFile to KlassModel: {}", classFile.thisClass);

        KlassModel klass = new KlassModel(
            classFile.thisClass,
            classFile.superClass,
            classFile.accessFlags
        );

        for (ClassFile.Method method : classFile.methods) {
            KlassModel.MethodInfo methodInfo = convertMethod(method);
            klass.addMethod(methodInfo);
        }

        convertFields(klass, classFile);

        logger.info("Converted ClassFile {} to KlassModel", classFile.thisClass);
        return klass;
    }

    private KlassModel.MethodInfo convertMethod(ClassFile.Method method) {
        KlassModel.MethodInfo info = new KlassModel.MethodInfo(
            method.name,
            method.descriptor,
            method.accessFlags
        );
        info.maxStack = method.maxStack;
        info.maxLocals = method.maxLocals;
        info.bytecode = method.code;
        return info;
    }

    private void convertFields(KlassModel klass, ClassFile classFile) {
        // Field conversion would go here if needed
    }

    public static String getMethodKey(String name, String descriptor) {
        return name + ":" + descriptor;
    }

    public static String getFieldKey(String name, String descriptor) {
        return name + ":" + descriptor;
    }
}
