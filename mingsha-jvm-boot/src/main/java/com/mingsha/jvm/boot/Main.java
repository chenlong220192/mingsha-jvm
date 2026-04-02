package com.mingsha.jvm.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mingsha.jvm.core.MingshaVMVersion;

/**
 * Main entry point for the Mingsha JVM.
 *
 * @version 1.0.0
 */
public class Main {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * JVM entry point.
     *
     * @param args command line arguments
     */
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

        logger.info("JVM initialization complete");
    }

    /** Prints usage information */
    private static void printUsage() {
        System.out.println("Usage: java <options> <class> [<arguments>]");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  -version    Show version and exit");
        System.out.println("  --help      Show this help message");
    }
}
