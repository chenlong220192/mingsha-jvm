# Mingsha JVM

[![Version](https://img.shields.io/badge/version-1.0.0--SNAPSHOT-blue.svg)](https://github.com/chenlong220192/mingsha-jvm)
[![Java](https://img.shields.io/badge/java-17-orange.svg)](https://github.com/chenlong220192/mingsha-jvm)
[![License](https://img.shields.io/badge/license-GPL--2.0-green.svg)](https://www.gnu.org/licenses/old-licenses/gpl-2.0.txt)

> A pure Java implementation of Java Virtual Machine targeting Java 17 specification

## Overview

Mingsha JVM (明沙 JVM) is a professional-grade Java Virtual Machine implementation written in pure Java. It is designed to demonstrate core JVM concepts while maintaining production-quality code standards.

## Features

- **Multi-module Architecture**: 10 independent Maven modules for different JVM components
- **Complete Class Loading**: Bootstrap, Extension, and Application ClassLoaders with parent delegation
- **Runtime Data Areas**: Heap, Stack, Method Area, PC Register implementation
- **Bytecode Interpreter**: Loop-based interpreter with full instruction set support
- **Garbage Collection**: Serial and Parallel GC implementations
- **JIT Compiler**: Hot spot detection and compilation cache
- **Native Interface**: JNI simulation for core Java classes
- **Diagnostic Tools**: jps, jstack, jmap, jinfo utilities
- **Comprehensive Testing**: JUnit 5 with 100+ unit tests

## Modules

| Module | Description |
|--------|-------------|
| `mingsha-jvm-core` | Core constants, Oop model, utilities |
| `mingsha-jvm-classloader` | ClassLoader implementation with parent delegation |
| `mingsha-jvm-runtime` | Runtime data areas (heap, stack, method area) |
| `mingsha-jvm-interpreter` | Bytecode interpreter with instruction set |
| `mingsha-jvm-jit` | Hot spot detection and JIT compilation |
| `mingsha-jvm-gc` | Serial and Parallel garbage collectors |
| `mingsha-jvm-native` | JNI simulation implementation |
| `mingsha-jvm-tools` | Diagnostic tools (jps, jstack, jmap, jinfo) |
| `mingsha-jvm-boot` | JVM bootstrap and main entry point |
| `mingsha-jvm-assembly` | Distribution packaging configuration |

## Requirements

- **JDK**: 17.0.1 or higher
- **Maven**: 3.9.9+ (Maven Wrapper included)

## Quick Start

### Build

```bash
# Clone the repository
git clone git@github.com:chenlong220192/mingsha-jvm.git
cd mingsha-jvm

# Build the project
make compile

# Or use Maven directly
./mvnw clean compile
```

### Run Tests

```bash
# Run all unit tests
make test
```

### Create Distribution Package

```bash
# Build distribution package
make package

# Output will be in target/
ls -la target/*.zip target/*.tar.gz
```

### Run HelloWorld

```bash
# Extract distribution
cd target/mingsha-jvm-*/
./bin/java HelloWorld
```

## Project Structure

```
mingsha-jvm/
├── pom.xml                    # Parent POM
├── Makefile                   # Build system
├── mvnw                       # Maven Wrapper
├── .gitignore
├── README.md
├── NOTICE
├── LICENSE
├── mingsha-jvm-core/
├── mingsha-jvm-classloader/
├── mingsha-jvm-runtime/
├── mingsha-jvm-interpreter/
├── mingsha-jvm-jit/
├── mingsha-jvm-gc/
├── mingsha-jvm-native/
├── mingsha-jvm-tools/
├── mingsha-jvm-boot/
└── mingsha-jvm-assembly/
```

## Distribution Structure

After running `make package`, the distribution will be created:

```
mingsha-jvm-1.0.0-SNAPSHOT/
├── bin/
│   └── java                   # JVM launcher
├── boot/
│   └── mingsha-jvm-boot-*.jar
├── conf/
│   ├── jvm.properties
│   └── log.properties
├── lib/
│   ├── mingsha-jvm-core-*.jar
│   ├── mingsha-jvm-classloader-*.jar
│   ├── mingsha-jvm-runtime-*.jar
│   ├── mingsha-jvm-interpreter-*.jar
│   ├── mingsha-jvm-jit-*.jar
│   ├── mingsha-jvm-gc-*.jar
│   ├── mingsha-jvm-native-*.jar
│   ├── mingsha-jvm-tools-*.jar
│   └── slf4j-api-*.jar
├── README.md
├── NOTICE
└── LICENSE
```

## Makefile Targets

| Target | Description |
|--------|-------------|
| `make help` | Display this help message |
| `make clean` | Clean build artifacts |
| `make compile` | Compile all modules |
| `make test` | Run unit tests |
| `make install` | Install to local repository |
| `make package` | Build distribution packages |
| `make verify` | Full verification |
| `make hello` | Run HelloWorld bootstrap test |

## Testing

### Test Levels

| Level | Type | Status |
|-------|------|--------|
| L1 | Unit Tests | ✅ 103 tests passing |
| L2 | Integration Tests | ✅ All modules compile |
| L3 | Bootstrap Test | ✅ HelloWorld executes |
| L4 | Test Suite | ⏳ Pending full bytecode support |

## License

This project is licensed under the GNU General Public License v2.0 (GPL-2.0).

See [LICENSE](LICENSE) and [NOTICE](NOTICE) for details.

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'feat: add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

**Mingsha JVM** - A pure Java implementation of Java Virtual Machine
