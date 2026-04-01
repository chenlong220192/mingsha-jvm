package com.mingsha.jvm.boot;

import com.mingsha.jvm.core.MingshaVMVersion;

/**
 * Main entry point for the Mingsha JVM.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Mingsha JVM version " + MingshaVMVersion.getVersion());
        System.out.println("OpenJDK Runtime Environment (build " + MingshaVMVersion.getJVMVersion() + ")");
        System.out.println("OpenJDK 64-Bit Server VM (build " + MingshaVMVersion.getJVMVersion() + ", mixed mode, sharing)");

        if (args.length == 0) {
            System.out.println();
            System.out.println("Usage: java <options> <class> [<arguments>]");
            System.out.println("Options:");
            System.out.println("  -version    Show version and exit");
            System.out.println("  --help      Show this help message");
        }
    }
}
