package com.mingsha.jvm.core.classfile;

import java.util.List;

public class ClassFile {

    public String thisClass;
    public String superClass;
    public int accessFlags;
    public ConstantPool constantPool;
    public List<Method> methods;

    public static class Method {
        public String name;
        public String descriptor;
        public int accessFlags;
        public int maxStack;
        public int maxLocals;
        public byte[] code;
    }
}
