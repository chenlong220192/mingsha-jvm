package com.mingsha.jvm.core.constants;

/**
 * JVM constant definitions for the Mingsha JVM implementation.
 * <p>
 * This class contains all JVM specification constants including:
 * <ul>
 *   <li>Class file magic number</li>
 *   <li>Access flags for classes, methods, and fields</li>
 *   <li>Bytecode operation codes (opcodes)</li>
 *   <li>Verification type info types</li>
 *   <li>Memory alignment constants</li>
 * </ul>
 *
 * @version 1.0.0
 * @see <a href="https://docs.oracle.com/javase/specs/jvms/se17/html/jvms-6.html">JVM Specification Chapter 6</a>
 */
public final class JVMConstants {

    /** Private constructor to prevent instantiation */
    private JVMConstants() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    // ========================================================================
    // Class File Format Constants
    // ========================================================================

    /** Class file magic number: 0xCAFEBABE */
    public static final int CLASS_FILE_MAGIC = 0xCAFEBABE;

    /** Major version number for Java 17 */
    public static final int JVM_VERSION_MAJOR = 17;

    /** Minor version number */
    public static final int JVM_VERSION_MINOR = 0;

    // ========================================================================
    // Access Flags
    // ========================================================================
    // https://docs.oracle.com/javase/specs/jvms/se17/html/jvms-4.html#jvms-4.1

    /** Access flag: public */
    public static final int ACC_PUBLIC = 0x0001;

    /** Access flag: private */
    public static final int ACC_PRIVATE = 0x0002;

    /** Access flag: protected */
    public static final int ACC_PROTECTED = 0x0004;

    /** Access flag: static */
    public static final int ACC_STATIC = 0x0008;

    /** Access flag: final */
    public static final int ACC_FINAL = 0x0010;

    /** Access flag: synchronized */
    public static final int ACC_SYNCHRONIZED = 0x0020;

    /** Access flag: super */
    public static final int ACC_SUPER = 0x0020;

    /** Access flag: volatile */
    public static final int ACC_VOLATILE = 0x0040;

    /** Access flag: transient */
    public static final int ACC_TRANSIENT = 0x0080;

    /** Access flag: native */
    public static final int ACC_NATIVE = 0x0100;

    /** Access flag: interface */
    public static final int ACC_INTERFACE = 0x0200;

    /** Access flag: abstract */
    public static final int ACC_ABSTRACT = 0x0400;

    /** Access flag: strict */
    public static final int ACC_STRICT = 0x0800;

    /** Access flag: synthetic */
    public static final int ACC_SYNTHETIC = 0x1000;

    /** Access flag: annotation */
    public static final int ACC_ANNOTATION = 0x2000;

    /** Access flag: enum */
    public static final int ACC_ENUM = 0x4000;

    // ========================================================================
    // Constant Pool Tags
    // ========================================================================

    /** Constant pool tag: class */
    public static final int CONSTANT_CLASS = 7;

    /** Constant pool tag: field reference */
    public static final int CONSTANT_FIELDREF = 9;

    /** Constant pool tag: method reference */
    public static final int CONSTANT_METHODREF = 10;

    /** Constant pool tag: interface method reference */
    public static final int CONSTANT_INTERFACEMETHODREF = 11;

    /** Constant pool tag: string */
    public static final int CONSTANT_STRING = 8;

    /** Constant pool tag: integer */
    public static final int CONSTANT_INTEGER = 3;

    /** Constant pool tag: float */
    public static final int CONSTANT_FLOAT = 4;

    /** Constant pool tag: long */
    public static final int CONSTANT_LONG = 5;

    /** Constant pool tag: double */
    public static final int CONSTANT_DOUBLE = 6;

    /** Constant pool tag: name and type */
    public static final int CONSTANT_NAMEANDTYPE = 12;

    /** Constant pool tag: UTF-8 */
    public static final int CONSTANT_UTF8 = 1;

    // ========================================================================
    // Verification Type Info
    // ========================================================================

    /** Verification type: TOP */
    public static final int VERIFY_TOP = 0;

    /** Verification type: INTEGER */
    public static final int VERIFY_INTEGER = 1;

    /** Verification type: FLOAT */
    public static final int VERIFY_FLOAT = 2;

    /** Verification type: DOUBLE */
    public static final int VERIFY_DOUBLE = 3;

    /** Verification type: LONG */
    public static final int VERIFY_LONG = 4;

    /** Verification type: NULL */
    public static final int VERIFY_NULL = 5;

    /** Verification type: UNINITIALIZED_THIS */
    public static final int VERIFY_UNINITIALIZEDTHIS = 6;

    /** Verification type: OBJECT */
    public static final int VERIFY_OBJECT = 7;

    /** Verification type: UNINITIALIZED */
    public static final int VERIFY_UNINITIALIZED = 8;

    // ========================================================================
    // Object and Array Constants
    // ========================================================================

    /** Object alignment in bytes (8 bytes for 64-bit JVM) */
    public static final int OBJECT_ALIGNMENT = 8;

    /** Object header size in bytes (mark word + klass pointer) */
    public static final int OBJECT_HEADER_SIZE = 16;

    /** Array header size in bytes */
    public static final int ARRAY_HEADER_SIZE = 20;

    /** Mark word size in bytes */
    public static final int MARK_WORD_SIZE = 8;

    /** Klass pointer size in bytes */
    public static final int KLASS_POINTER_SIZE = 8;

    // ========================================================================
    // Bytecode Opcodes - Constants
    // ========================================================================

    /** Opcode: 0x00 - Do nothing */
    public static final int NOP = 0;

    /** Opcode: 0x01 - Push null */
    public static final int ACONST_NULL = 1;

    /** Opcode: 0x02 - Push int constant -1 */
    public static final int ICONST_M1 = 2;

    /** Opcode: 0x03 - Push int constant 0 */
    public static final int ICONST_0 = 3;

    /** Opcode: 0x04 - Push int constant 1 */
    public static final int ICONST_1 = 4;

    /** Opcode: 0x05 - Push int constant 2 */
    public static final int ICONST_2 = 5;

    /** Opcode: 0x06 - Push int constant 3 */
    public static final int ICONST_3 = 6;

    /** Opcode: 0x07 - Push int constant 4 */
    public static final int ICONST_4 = 7;

    /** Opcode: 0x08 - Push int constant 5 */
    public static final int ICONST_5 = 8;

    /** Opcode: 0x09 - Push long constant 0 */
    public static final int LCONST_0 = 9;

    /** Opcode: 0x0a - Push long constant 1 */
    public static final int LCONST_1 = 10;

    /** Opcode: 0x0b - Push float constant 0 */
    public static final int FCONST_0 = 11;

    /** Opcode: 0x0c - Push float constant 1 */
    public static final int FCONST_1 = 12;

    /** Opcode: 0x0d - Push float constant 2 */
    public static final int FCONST_2 = 13;

    /** Opcode: 0x0e - Push double constant 0 */
    public static final int DCONST_0 = 14;

    /** Opcode: 0x0f - Push double constant 1 */
    public static final int DCONST_1 = 15;

    // ========================================================================
    // Bytecode Opcodes - Push
    // ========================================================================

    /** Opcode: 0x10 - Push byte */
    public static final int BIPUSH = 16;

    /** Opcode: 0x11 - Push short */
    public static final int SIPUSH = 17;

    /** Opcode: 0x12 - Push item from constant pool */
    public static final int LDC = 18;

    /** Opcode: 0x13 - Push item from constant pool (wide index) */
    public static final int LDC_W = 19;

    /** Opcode: 0x14 - Push long or double from constant pool */
    public static final int LDC2_W = 20;

    // ========================================================================
    // Bytecode Opcodes - Load
    // ========================================================================

    /** Opcode: 0x15 - Load int from local variable */
    public static final int ILOAD = 21;

    /** Opcode: 0x16 - Load long from local variable */
    public static final int LLOAD = 22;

    /** Opcode: 0x17 - Load float from local variable */
    public static final int FLOAD = 23;

    /** Opcode: 0x18 - Load double from local variable */
    public static final int DLOAD = 24;

    /** Opcode: 0x19 - Load reference from local variable */
    public static final int ALOAD = 25;

    // ========================================================================
    // Bytecode Opcodes - Store
    // ========================================================================

    /** Opcode: 0x36 - Store int into local variable */
    public static final int ISTORE = 54;

    /** Opcode: 0x37 - Store long into local variable */
    public static final int LSTORE = 55;

    /** Opcode: 0x38 - Store float into local variable */
    public static final int FSTORE = 56;

    /** Opcode: 0x39 - Store double into local variable */
    public static final int DSTORE = 57;

    /** Opcode: 0x3a - Store reference into local variable */
    public static final int ASTORE = 58;

    // ========================================================================
    // Bytecode Opcodes - Stack
    // ========================================================================

    /** Opcode: 0x57 - Pop top operand */
    public static final int POP = 87;

    /** Opcode: 0x58 - Pop top two operands */
    public static final int POP2 = 88;

    /** Opcode: 0x59 - Duplicate top operand */
    public static final int DUP = 89;

    /** Opcode: 0x5a - Duplicate top operand and insert beneath */
    public static final int DUP_X1 = 90;

    /** Opcode: 0x5b - Duplicate top two operands */
    public static final int DUP_X2 = 91;

    /** Opcode: 0x5c - Duplicate top two operands and insert beneath */
    public static final int DUP2 = 92;

    /** Opcode: 0x5f - Swap top two operands */
    public static final int SWAP = 95;

    // ========================================================================
    // Bytecode Opcodes - Arithmetic
    // ========================================================================

    /** Opcode: 0x60 - Add int */
    public static final int IADD = 96;

    /** Opcode: 0x61 - Add long */
    public static final int LADD = 97;

    /** Opcode: 0x62 - Add float */
    public static final int FADD = 98;

    /** Opcode: 0x63 - Add double */
    public static final int DADD = 99;

    /** Opcode: 0x64 - Subtract int */
    public static final int ISUB = 100;

    /** Opcode: 0x65 - Subtract long */
    public static final int LSUB = 101;

    /** Opcode: 0x66 - Subtract float */
    public static final int FSUB = 102;

    /** Opcode: 0x67 - Subtract double */
    public static final int DSUB = 103;

    /** Opcode: 0x68 - Multiply int */
    public static final int IMUL = 104;

    /** Opcode: 0x69 - Multiply long */
    public static final int LMUL = 105;

    /** Opcode: 0x6a - Multiply float */
    public static final int FMUL = 106;

    /** Opcode: 0x6b - Multiply double */
    public static final int DMUL = 107;

    /** Opcode: 0x6c - Divide int */
    public static final int IDIV = 108;

    /** Opcode: 0x6d - Divide long */
    public static final int LDIV = 109;

    /** Opcode: 0x6e - Divide float */
    public static final int FDIV = 110;

    /** Opcode: 0x6f - Divide double */
    public static final int DDIV = 111;

    /** Opcode: 0x70 - Remainder int */
    public static final int IREM = 112;

    /** Opcode: 0x71 - Remainder long */
    public static final int LREM = 113;

    /** Opcode: 0x72 - Remainder float */
    public static final int FREM = 114;

    /** Opcode: 0x73 - Remainder double */
    public static final int DREM = 115;

    /** Opcode: 0x74 - Negate int */
    public static final int INEG = 116;

    /** Opcode: 0x75 - Negate long */
    public static final int LNEG = 117;

    /** Opcode: 0x76 - Negate float */
    public static final int FNEG = 118;

    /** Opcode: 0x77 - Negate double */
    public static final int DNEG = 119;

    /** Opcode: 0x78 - Shift left int */
    public static final int ISHL = 120;

    /** Opcode: 0x79 - Shift left long */
    public static final int LSHL = 121;

    /** Opcode: 0x7a - Shift right int */
    public static final int ISHR = 122;

    /** Opcode: 0x7b - Shift right long */
    public static final int LSHR = 123;

    /** Opcode: 0x7c - Logical shift right int */
    public static final int IUSHR = 124;

    /** Opcode: 0x7d - Logical shift right long */
    public static final int LUSHR = 125;

    /** Opcode: 0x7e - Bitwise AND int */
    public static final int IAND = 126;

    /** Opcode: 0x7f - Bitwise AND long */
    public static final int LAND = 127;

    /** Opcode: 0x80 - Bitwise OR int */
    public static final int IOR = 128;

    /** Opcode: 0x81 - Bitwise OR long */
    public static final int LOR = 129;

    /** Opcode: 0x82 - Bitwise XOR int */
    public static final int IXOR = 130;

    /** Opcode: 0x83 - Bitwise XOR long */
    public static final int LXOR = 131;

    /** Opcode: 0x84 - Increment local variable */
    public static final int IINC = 132;

    // ========================================================================
    // Bytecode Opcodes - Type Conversion
    // ========================================================================

    /** Opcode: 0x85 - int to long */
    public static final int I2L = 133;

    /** Opcode: 0x86 - int to float */
    public static final int I2F = 134;

    /** Opcode: 0x87 - int to double */
    public static final int I2D = 135;

    /** Opcode: 0x88 - long to int */
    public static final int L2I = 136;

    /** Opcode: 0x89 - long to float */
    public static final int L2F = 137;

    /** Opcode: 0x8a - long to double */
    public static final int L2D = 138;

    /** Opcode: 0x8b - float to int */
    public static final int F2I = 139;

    /** Opcode: 0x8c - float to long */
    public static final int F2L = 140;

    /** Opcode: 0x8d - float to double */
    public static final int F2D = 141;

    /** Opcode: 0x8e - double to int */
    public static final int D2I = 142;

    /** Opcode: 0x8f - double to long */
    public static final int D2L = 143;

    /** Opcode: 0x90 - double to float */
    public static final int D2F = 144;

    // ========================================================================
    // Bytecode Opcodes - Comparison
    // ========================================================================

    /** Opcode: 0x94 - Compare long */
    public static final int LCMP = 97;

    /** Opcode: 0x95 - Compare float (ordered) */
    public static final int FCMPL = 98;

    /** Opcode: 0x96 - Compare float (unordered) */
    public static final int FCMPG = 99;

    /** Opcode: 0x97 - Compare double (ordered) */
    public static final int DCMPL = 100;

    /** Opcode: 0x98 - Compare double (unordered) */
    public static final int DCMPG = 101;

    // ========================================================================
    // Bytecode Opcodes - Branch
    // ========================================================================

    /** Opcode: 0x99 - Branch if int comparison with zero equals */
    public static final int IFEQ = 153;

    /** Opcode: 0x9a - Branch if int comparison with zero not equals */
    public static final int IFNE = 154;

    /** Opcode: 0x9b - Branch if int comparison with zero less than */
    public static final int IFLT = 155;

    /** Opcode: 0x9c - Branch if int comparison with zero greater or equal */
    public static final int IFGE = 156;

    /** Opcode: 0x9d - Branch if int comparison with zero greater than */
    public static final int IFGT = 157;

    /** Opcode: 0x9e - Branch if int comparison with zero less or equal */
    public static final int IFLE = 158;

    /** Opcode: 0x9f - Branch if int comparison equals */
    public static final int IF_ICMPEQ = 159;

    /** Opcode: 0xa0 - Branch if int comparison not equals */
    public static final int IF_ICMPNE = 160;

    /** Opcode: 0xa1 - Branch if int comparison less than */
    public static final int IF_ICMPLT = 161;

    /** Opcode: 0xa2 - Branch if int comparison greater or equal */
    public static final int IF_ICMPGE = 162;

    /** Opcode: 0xa3 - Branch if int comparison greater than */
    public static final int IF_ICMPGT = 163;

    /** Opcode: 0xa4 - Branch if int comparison less or equal */
    public static final int IF_ICMPLE = 164;

    /** Opcode: 0xa5 - Branch if reference equals */
    public static final int IF_ACMPEQ = 165;

    /** Opcode: 0xa6 - Branch if reference not equals */
    public static final int IF_ACMPNE = 166;

    /** Opcode: 0xa7 - Unconditional branch */
    public static final int GOTO = 167;

    // ========================================================================
    // Bytecode Opcodes - Return
    // ========================================================================

    /** Opcode: 0xac - Return int from method */
    public static final int IRETURN = 172;

    /** Opcode: 0xad - Return long from method */
    public static final int LRETURN = 173;

    /** Opcode: 0xae - Return float from method */
    public static final int FRETURN = 174;

    /** Opcode: 0xaf - Return double from method */
    public static final int DRETURN = 175;

    /** Opcode: 0xb0 - Return reference from method */
    public static final int ARETURN = 176;

    /** Opcode: 0xb1 - Return void from method */
    public static final int RETURN = 177;

    // ========================================================================
    // Bytecode Opcodes - Field Access
    // ========================================================================

    /** Opcode: 0xb2 - Get static field */
    public static final int GETSTATIC = 178;

    /** Opcode: 0xb3 - Put static field */
    public static final int PUTSTATIC = 179;

    /** Opcode: 0xb4 - Get instance field */
    public static final int GETFIELD = 180;

    /** Opcode: 0xb5 - Put instance field */
    public static final int PUTFIELD = 181;

    // ========================================================================
    // Bytecode Opcodes - Method Invocation
    // ========================================================================

    /** Opcode: 0xb6 - Invoke instance method (virtual) */
    public static final int INVOKEVIRTUAL = 182;

    /** Opcode: 0xb7 - Invoke instance method (special) */
    public static final int INVOKESPECIAL = 183;

    /** Opcode: 0xb8 - Invoke static method */
    public static final int INVOKESTATIC = 184;

    /** Opcode: 0xb9 - Invoke interface method */
    public static final int INVOKEINTERFACE = 185;

    // ========================================================================
    // Bytecode Opcodes - Object Creation
    // ========================================================================

    /** Opcode: 0xbb - Create new object */
    public static final int NEW = 187;

    /** Opcode: 0xbc - Create new array (primitive type) */
    public static final int NEWARRAY = 188;

    /** Opcode: 0xbd - Create new array (reference type) */
    public static final int ANEWARRAY = 189;

    /** Opcode: 0xbe - Get array length */
    public static final int ARRAYLENGTH = 190;

    // ========================================================================
    // Bytecode Opcodes - Exception Handling
    // ========================================================================

    /** Opcode: 0xbf - Throw exception */
    public static final int ATHROW = 191;

    // ========================================================================
    // Bytecode Opcodes - Type Checking
    // ========================================================================

    /** Opcode: 0xc0 - Checkcast type */
    public static final int CHECKCAST = 192;

    /** Opcode: 0xc1 - Instanceof check */
    public static final int INSTANCEOF = 193;

    // ========================================================================
    // Bytecode Opcodes - Monitor
    // ========================================================================

    /** Opcode: 0xc2 - Enter monitor */
    public static final int MONITORENTER = 194;

    /** Opcode: 0xc3 - Exit monitor */
    public static final int MONITOREXIT = 195;
}
