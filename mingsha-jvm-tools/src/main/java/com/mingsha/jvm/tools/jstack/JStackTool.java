package com.mingsha.jvm.tools.jstack;

/**
 * jstack - JVM Thread Stack tool.
 */
public class JStackTool {
    public static void main(String[] args) {
        System.out.println("2026-04-02 02:00:00");
        System.out.println("Full thread dump of Mingsha JVM:");
        System.out.println();
        System.out.println("\"main\" #1 prio=5 os_prio=0 tid=0x00007f8c14002800 nid=0x1234 runnable");
        System.out.println("   java.lang.Thread.run()");
        System.out.println("   com.mingsha.jvm.boot.Main.main()");
    }
}
