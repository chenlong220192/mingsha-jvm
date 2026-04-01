# Mingsha JVM

A Java Virtual Machine implementation targeting Java 17.

## Version
v0.5.0 - Final release with all modules

## Modules
- mingsha-jvm-core: Constants, Oop model, utilities
- mingsha-jvm-classloader: Bootstrap/Extension/App ClassLoaders
- mingsha-jvm-runtime: Heap, Stack, MethodArea, Thread
- mingsha-jvm-interpreter: Bytecode interpreter
- mingsha-jvm-jit: Hot spot detection and JIT compiler
- mingsha-jvm-gc: Serial and Parallel GC
- mingsha-jvm-native: JNI simulation
- mingsha-jvm-tools: jps, jstack, jmap, jinfo
- mingsha-jvm-boot: Main entry point

## Build
./mvnw clean install

## License
GPL v2
