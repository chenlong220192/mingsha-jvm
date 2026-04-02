package com.mingsha.jvm.boot;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class MainTest {

    @Test
    void testMainWithNoArgs() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            Main.main(new String[]{});
            String output = outContent.toString();
            assertTrue(output.contains("Mingsha JVM"));
            assertTrue(output.contains("Usage:"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void testMainWithVersionFlag() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            Main.main(new String[]{"-version"});
            String output = outContent.toString();
            assertTrue(output.contains("Mingsha JVM"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void testMainWithHelpFlag() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            Main.main(new String[]{"--help"});
            String output = outContent.toString();
            assertTrue(output.contains("Usage:"));
            assertTrue(output.contains("Options:"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void testMainWithEmptyArgs() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            Main.main(new String[]{});
            String output = outContent.toString();
            assertTrue(output.contains("Options:"));
        } finally {
            System.setOut(originalOut);
        }
    }
}