package com.mingsha.jvm.tools.jmap;

/**
 * jmap - JVM Memory Map tool.
 */
public class JMapTool {
    public static void main(String[] args) {
        System.out.println("Heap Configuration:");
        System.out.println("  Heap size: 512MB");
        System.out.println("  Max heap size: 2048MB");
        System.out.println();
        System.out.println("Heap Usage:");
        System.out.println("  Used: 100MB");
        System.out.println("  Free: 412MB");
        System.out.println("  Usage: 19.5%");
    }
}
