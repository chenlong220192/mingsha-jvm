package com.mingsha.jvm.core.classfile;

import com.mingsha.jvm.core.constants.JVMConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class ClassFileParser {

    private static final Logger logger = LoggerFactory.getLogger(ClassFileParser.class);

    private ByteBuffer buffer;
    private int constantPoolCount;
    private ConstantPool constantPool;
    private int accessFlags;
    private String thisClass;
    private String superClass;

    public ClassFile parse(byte[] classData) throws IOException {
        buffer = ByteBuffer.wrap(classData);
        buffer.order(ByteOrder.BIG_ENDIAN);

        int magic = buffer.getInt();
        if (magic != JVMConstants.CLASS_FILE_MAGIC) {
            throw new IOException("Invalid class file: magic = 0x" + Integer.toHexString(magic));
        }

        int minorVersion = buffer.getShort() & 0xFFFF;
        int majorVersion = buffer.getShort() & 0xFFFF;
        logger.debug("Class file version: {}.{}", majorVersion, minorVersion);

        constantPoolCount = buffer.getShort() & 0xFFFF;
        logger.debug("Constant pool count: {}", constantPoolCount);

        constantPool = new ConstantPool(constantPoolCount);
        parseConstantPool();

        accessFlags = buffer.getShort() & 0xFFFF;
        thisClass = parseClassEntry(buffer.getShort() & 0xFFFF);
        superClass = parseClassEntry(buffer.getShort() & 0xFFFF);

        logger.debug("Class: {} (super: {})", thisClass, superClass);

        int interfacesCount = buffer.getShort() & 0xFFFF;
        buffer.position(buffer.position() + interfacesCount * 2);

        int fieldsCount = buffer.getShort() & 0xFFFF;
        buffer.position(buffer.position() + fieldsCount * 8);

        int methodsCount = buffer.getShort() & 0xFFFF;
        List<ClassFile.Method> methods = parseMethods(methodsCount);

        buffer.position(buffer.position() + buffer.getShort() & 0xFFFF);

        ClassFile classFile = new ClassFile();
        classFile.thisClass = thisClass;
        classFile.superClass = superClass;
        classFile.accessFlags = accessFlags;
        classFile.constantPool = constantPool;
        classFile.methods = methods;

        logger.info("Parsed class: {}", thisClass);
        return classFile;
    }

    private void parseConstantPool() throws IOException {
        for (int i = 1; i < constantPoolCount; i++) {
            int tag = buffer.get() & 0xFF;
            switch (tag) {
                case JVMConstants.CONSTANT_CLASS:
                    constantPool.setEntry(i, new ConstantPool.Entry(ConstantPool.EntryType.CLASS, buffer.getShort() & 0xFFFF));
                    break;
                case JVMConstants.CONSTANT_FIELDREF:
                    constantPool.setEntry(i, new ConstantPool.Entry(ConstantPool.EntryType.FIELDREF, 
                        buffer.getShort() & 0xFFFF, buffer.getShort() & 0xFFFF));
                    break;
                case JVMConstants.CONSTANT_METHODREF:
                    constantPool.setEntry(i, new ConstantPool.Entry(ConstantPool.EntryType.METHODREF,
                        buffer.getShort() & 0xFFFF, buffer.getShort() & 0xFFFF));
                    break;
                case JVMConstants.CONSTANT_STRING:
                    constantPool.setEntry(i, new ConstantPool.Entry(ConstantPool.EntryType.STRING, buffer.getShort() & 0xFFFF));
                    break;
                case JVMConstants.CONSTANT_INTEGER:
                    constantPool.setEntry(i, new ConstantPool.Entry(ConstantPool.EntryType.INTEGER, buffer.getInt()));
                    break;
                case JVMConstants.CONSTANT_FLOAT:
                    constantPool.setEntry(i, new ConstantPool.Entry(ConstantPool.EntryType.FLOAT, buffer.getFloat()));
                    break;
                case JVMConstants.CONSTANT_LONG:
                    constantPool.setEntry(i, new ConstantPool.Entry(ConstantPool.EntryType.LONG, buffer.getLong()));
                    i++;
                    break;
                case JVMConstants.CONSTANT_DOUBLE:
                    constantPool.setEntry(i, new ConstantPool.Entry(ConstantPool.EntryType.DOUBLE, buffer.getLong()));
                    i++;
                    break;
                case JVMConstants.CONSTANT_NAMEANDTYPE:
                    constantPool.setEntry(i, new ConstantPool.Entry(ConstantPool.EntryType.NAMEANDTYPE,
                        buffer.getShort() & 0xFFFF, buffer.getShort() & 0xFFFF));
                    break;
                case JVMConstants.CONSTANT_UTF8:
                    int length = buffer.getShort() & 0xFFFF;
                    byte[] bytes = new byte[length];
                    buffer.get(bytes);
                    constantPool.setEntry(i, new ConstantPool.Entry(ConstantPool.EntryType.UTF8, new String(bytes)));
                    break;
                default:
                    throw new IOException("Unknown constant pool tag: " + tag);
            }
        }
    }

    private String parseClassEntry(int index) {
        if (index == 0) return "";
        ConstantPool.Entry entry = constantPool.getEntry(index);
        if (entry != null && entry.type == ConstantPool.EntryType.CLASS) {
            return constantPool.getUtf8(entry.nameIndex);
        }
        return "";
    }

    private List<ClassFile.Method> parseMethods(int count) throws IOException {
        List<ClassFile.Method> methods = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int methodAccessFlags = buffer.getShort() & 0xFFFF;
            int nameIndex = buffer.getShort() & 0xFFFF;
            int descriptorIndex = buffer.getShort() & 0xFFFF;
            int attributesCount = buffer.getShort() & 0xFFFF;

            String name = constantPool.getUtf8(nameIndex);
            String descriptor = constantPool.getUtf8(descriptorIndex);

            ClassFile.Method method = new ClassFile.Method();
            method.name = name;
            method.descriptor = descriptor;
            method.accessFlags = methodAccessFlags;

            for (int j = 0; j < attributesCount; j++) {
                int attrNameIndex = buffer.getShort() & 0xFFFF;
                int attrLength = buffer.getInt();
                String attrName = constantPool.getUtf8(attrNameIndex);

                if ("Code".equals(attrName)) {
                    method.maxStack = buffer.getShort() & 0xFFFF;
                    method.maxLocals = buffer.getShort() & 0xFFFF;
                    int codeLength = buffer.getInt();
                    byte[] code = new byte[codeLength];
                    buffer.get(code);
                    method.code = code;
                } else {
                    buffer.position(buffer.position() + attrLength);
                }
            }

            methods.add(method);
            logger.trace("Parsed method: {} {}", name, descriptor);
        }
        return methods;
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }
}
