package com.mingsha.jvm.tools.jps;

import java.util.ArrayList;
import java.util.List;

/**
 * jps - JVM Process Status tool.
 */
public class JpsTool {
    public static void main(String[] args) {
        System.out.println("PID   Type   Name");
        System.out.println("----- ------ -----");
        System.out.println("12345 boot   Mingsha JVM");
    }

    public static class JvmProcess {
        public final long pid;
        public final String type;
        public final String name;

        public JvmProcess(long pid, String type, String name) {
            this.pid = pid;
            this.type = type;
            this.name = name;
        }
    }
}
