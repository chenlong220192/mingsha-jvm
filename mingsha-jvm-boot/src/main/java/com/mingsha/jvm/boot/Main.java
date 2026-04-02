package com.mingsha.jvm.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mingsha.jvm.core.MingshaVMVersion;
import com.mingsha.jvm.core.constants.JVMConstants;
import com.mingsha.jvm.interpreter.LoopInterpreter;
import com.mingsha.jvm.runtime.thread.MingshaThread;
import com.mingsha.jvm.runtime.methodarea.KlassModel;
import com.mingsha.jvm.runtime.stack.JavaStack;
import com.mingsha.jvm.runtime.stack.StackFrame;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Mingsha JVM starting");
        System.out.println("Mingsha JVM version " + MingshaVMVersion.getVersion());
        System.out.println("OpenJDK Runtime Environment (build " + MingshaVMVersion.getJVMVersion() + ")");
        System.out.println("OpenJDK 64-Bit Server VM (build " + MingshaVMVersion.getJVMVersion() + ", mixed mode, sharing)");
        System.out.println();

        if (args.length == 0) {
            printUsage();
            return;
        }

        if ("-version".equals(args[0])) {
            return;
        }

        if ("--help".equals(args[0]) || "-help".equals(args[0])) {
            printUsage();
            return;
        }

        String className = args[0];
        String[] classArgs = java.util.Arrays.copyOfRange(args, 1, args.length);

        executeClass(className, classArgs);
        logger.info("JVM execution complete");
    }

    private static void executeClass(String className, String[] args) {
        logger.info("Executing class: {}", className);
        
        try {
            MingshaThread thread = new MingshaThread("main", Thread.NORM_PRIORITY);
            KlassModel klass = createHelloWorldClass(className);
            
            KlassModel.MethodInfo mainMethod = findMainMethod(klass);
            if (mainMethod == null) {
                System.out.println("Error: Main method not found in class " + className);
                return;
            }

            StackFrame frame = new StackFrame(mainMethod.maxLocals, mainMethod.maxStack);
            frame.setMethodName("main");
            frame.setClassName(className);
            
            String[] initArgs = new String[args.length];
            System.arraycopy(args, 0, initArgs, 0, args.length);
            frame.setLocalVariable(0, initArgs);
            
            thread.getStack().pushFrame(frame);

            LoopInterpreter interpreter = LoopInterpreter.getInstance();
            interpreter.interpret(thread, klass, mainMethod, mainMethod.bytecode);
            
        } catch (LoopInterpreter.ReturnException e) {
            logger.debug("Method returned: {}", e.returnValue);
        } catch (Exception e) {
            logger.error("Execution error: {}", e.getMessage(), e);
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static KlassModel createHelloWorldClass(String className) {
        KlassModel klass = new KlassModel(className, "java/lang/Object", JVMConstants.ACC_PUBLIC);
        
        KlassModel.MethodInfo mainMethod = new KlassModel.MethodInfo(
            "main", "([Ljava/lang/String;)V", JVMConstants.ACC_PUBLIC | JVMConstants.ACC_STATIC);
        mainMethod.maxStack = 3;
        mainMethod.maxLocals = 1;
        
        mainMethod.bytecode = generateHelloWorldBytecode(className);
        klass.addMethod(mainMethod);
        
        return klass;
    }

    private static byte[] generateHelloWorldBytecode(String className) {
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        baos.write(JVMConstants.GETSTATIC);
        baos.write(0x00); baos.write(0x01);
        
        baos.write(JVMConstants.LDC);
        baos.write(0x00);
        
        baos.write(JVMConstants.INVOKEVIRTUAL);
        baos.write(0x00); baos.write(0x03);
        
        baos.write(JVMConstants.RETURN);
        
        return baos.toByteArray();
    }

    private static KlassModel.MethodInfo findMainMethod(KlassModel klass) {
        for (KlassModel.MethodInfo method : klass.getMethods().values()) {
            if ("main".equals(method.name) && "([Ljava/lang/String;)V".equals(method.descriptor)) {
                return method;
            }
        }
        return null;
    }

    private static void printUsage() {
        System.out.println("Usage: java <options> <class> [<arguments>]");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  -version    Show version and exit");
        System.out.println("  --help      Show this help message");
    }
}
