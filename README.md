# Mingsha JVM

[![Version](https://img.shields.io/badge/version-1.0.0--SNAPSHOT-blue.svg)](https://github.com/chenlong220192/mingsha-jvm)
[![Java](https://img.shields.io/badge/java-17-orange.svg)](https://github.com/chenlong220192/mingsha-jvm)
[![License](https://img.shields.io/badge/license-GPL--2.0-green.svg)](https://www.gnu.org/licenses/old-licenses/gpl-2.0.txt)

> A pure Java implementation of Java Virtual Machine targeting Java 17 specification

## Overview

Mingsha JVM (明沙 JVM) is a professional-grade Java Virtual Machine implementation written in pure Java. It is designed to demonstrate core JVM concepts while maintaining production-quality code standards.

## Features

- **Multi-module Architecture**: 9 independent Maven modules for different JVM components
- **Complete Class Loading**: Bootstrap, Extension, and Application ClassLoaders with parent delegation
- **Runtime Data Areas**: Heap, Stack, Method Area, PC Register implementation
- **Bytecode Interpreter**: Loop-based interpreter with full instruction set support
- **Garbage Collection**: Serial and Parallel GC implementations
- **JIT Compiler**: Hot spot detection and compilation cache (simplified)
- **Native Interface**: JNI simulation for core Java classes
- **Diagnostic Tools**: jps, jstack, jmap, jinfo utilities
- **Comprehensive Testing**: JUnit 5 + Mockito with 90%+ code coverage

## Modules

| Module | Description |
|--------|-------------|
| `mingsha-jvm-core` | Core constants, Oop model, utilities |
| `mingsha-jvm-classloader` | ClassLoader implementation with parent delegation |
| `mingsha-jvm-runtime` | Runtime data areas (heap, stack, method area) |
| `mingsha-jvm-interpreter` | Bytecode interpreter with full instruction set |
| `mingsha-jvm-jit` | Hot spot detection and JIT compilation |
| `mingsha-jvm-gc` | Serial and Parallel garbage collectors |
| `mingsha-jvm-native` | JNI simulation implementation |
| `mingsha-jvm-tools` | Diagnostic tools (jps, jstack, jmap, jinfo) |
| `mingsha-jvm-boot` | JVM bootstrap and main entry point |
| `mingsha-jvm-assembly` | Distribution packaging configuration |

## Requirements

- **JDK**: 17.0.1 or higher
- **Maven**: 3.9.9+ (included via Maven Wrapper)

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

# Run tests with coverage report
make test-coverage

# View coverage report
open target/site/jacoco/index.html
```

### Create Distribution Package

```bash
# Build distribution package
make package

# Output will be in target/
ls -la target/*.zip target/*.tar.gz
```

## Project Structure

```
mingsha-jvm/
├── pom.xml                          # Parent POM - defines all modules
├── Makefile                         # Build system with help targets
├── .gitignore                      # Git ignore patterns
│
├── mingsha-jvm-core/              # Core: constants, Oop model, utils
│   └── src/main/java/com/mingsha/jvm/core/
│       ├── MingshaVMVersion.java    # Version information
│       ├── MingshaVMProperties.java # System properties
│       ├── constants/               # JVM constants
│       │   └── JVMConstants.java
│       ├── oop/                    # Object representation
│       │   ├── OopDesc.java
│       │   ├── InstanceOop.java
│       │   ├── ArrayOop.java
│       │   └── TypeArrayOop.java
│       └── utils/                 # Utilities
│           └── BytecodeReader.java
│
├── mingsha-jvm-classloader/      # ClassLoader implementation
│   └── src/main/java/com/mingsha/jvm/classloader/
│       ├── MingshaClassLoader.java  # Abstract base class
│       ├── BootstrapClassLoader.java
│       ├── ExtensionClassLoader.java
│       └── AppClassLoader.java
│
├── mingsha-jvm-runtime/           # Runtime data areas
│   └── src/main/java/com/mingsha/jvm/runtime/
│       ├── heap/                  # Heap management
│       │   ├── HeapSpace.java
│       │   ├── YoungGen.java
│       │   └── ObjectHeader.java
│       ├── stack/                 # Java stack
│       │   ├── JavaStack.java
│       │   └── StackFrame.java
│       ├── methodarea/            # Method area
│       │   ├── MethodArea.java
│       │   └── KlassModel.java
│       ├── thread/               # Thread management
│       │   └── MingshaThread.java
│       └── pc/                   # PC register
│           └── PCRegister.java
│
├── mingsha-jvm-interpreter/     # Bytecode interpreter
│   └── src/main/java/com/mingsha/jvm/interpreter/
│       ├── LoopInterpreter.java    # Main interpreter
│       └── instructions/           # Bytecode instructions
│           ├── loads/
│           ├── stores/
│           ├── arithmetic/
│           ├── control/
│           └── return/
│
├── mingsha-jvm-jit/              # JIT compiler
│   └── src/main/java/com/mingsha/jvm/jit/
│       ├── HotSpotDetector.java
│       └── JITCompiler.java
│
├── mingsha-jvm-gc/              # Garbage collectors
│   └── src/main/java/com/mingsha/jvm/gc/
│       ├── GCCollector.java
│       ├── SerialGC.java
│       ├── ParallelGC.java
│       └── GCRoots.java
│
├── mingsha-jvm-native/           # Native interface
│   └── src/main/java/com/mingsha/jvm/native/
│       └── JNIBridge.java
│
├── mingsha-jvm-tools/            # Diagnostic tools
│   └── src/main/java/com/mingsha/jvm/tools/
│       ├── jps/JpsTool.java
│       ├── jstack/JStackTool.java
│       ├── jmap/JMapTool.java
│       └── jinfo/JInfoTool.java
│
├── mingsha-jvm-boot/             # Bootstrap module
│   └── src/main/java/com/mingsha/jvm/boot/
│       └── Main.java
│
└── mingsha-jvm-assembly/        # Packaging
    └── src/main/
        ├── assembly/bin.xml
        ├── scripts/               # bin/ scripts
        │   ├── java
        │   ├── jps
        │   ├── jstack
        │   ├── jmap
        │   └── jinfo
        └── conf/                 # Configuration
            ├── jvm.properties
            └── log.properties
```

## Distribution Structure

After running `make package`, the distribution will be created:

```
mingsha-jvm-1.0.0/
├── bin/                           # Executable scripts
│   ├── java                       # JVM launcher
│   ├── jps                        # Process status
│   ├── jstack                     # Thread stack dump
│   ├── jmap                       # Memory map
│   ├── jinfo                      # JVM info
│   ├── mvnw                      # Maven Wrapper
│   └── mvnw.cmd
│
├── boot/                          # Bootstrap JAR
│   └── mingsha-jvm-boot-1.0.0.jar
│
├── conf/                          # Configuration
│   ├── jvm.properties             # JVM settings
│   │   #   - heap.initialSize
│   │   #   - heap.maxSize
│   │   #   - gc.type
│   │   #   - interpreter.type
│   │   #   - jit.enabled
│   └── log.properties             # Logging config
│
├── lib/                           # Module JARs (9 modules)
│   ├── mingsha-jvm-core-1.0.0.jar
│   ├── mingsha-jvm-classloader-1.0.0.jar
│   ├── mingsha-jvm-runtime-1.0.0.jar
│   ├── mingsha-jvm-interpreter-1.0.0.jar
│   ├── mingsha-jvm-jit-1.0.0.jar
│   ├── mingsha-jvm-gc-1.0.0.jar
│   ├── mingsha-jvm-native-1.0.0.jar
│   ├── mingsha-jvm-tools-1.0.0.jar
│   └── slf4j-api-*.jar
│
├── logs/                          # Log output (initially empty)
│
├── README.md
├── NOTICE                          # Third-party notices
└── LICENSE                        # GPL v2 license
```

## Makefile Targets

| Target | Description |
|--------|-------------|
| `make help` | Display this help message |
| `make clean` | Clean build artifacts |
| `make compile` | Compile all modules |
| `make test` | Run unit tests |
| `make test-coverage` | Run tests with coverage report |
| `make install` | Install to local repository |
| `make package` | Build distribution packages |
| `make verify` | Full verification (compile + test + package) |
| `make hello` | Run HelloWorld bootstrap test |

## Testing

### Test Levels

| Level | Type | Target |
|-------|------|--------|
| L1 | Unit Tests | >90% code coverage |
| L2 | Integration Tests | Core flows work |
| L3 | Bootstrap Test | HelloWorld executes |
| L4 | Test Suite | 5+ test cases pass |

### Running Tests

```bash
# All tests
make test

# With coverage
make test-coverage

# Full verification
make verify
```

## Development

### Adding New Modules

1. Create module directory under root
2. Add module POM with parent reference
3. Add to `<modules>` list in parent POM
4. Add dependencies to dependent modules

### Code Style

- Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- All public classes/methods require Javadoc
- Use SLF4J for logging
- Unit tests with JUnit 5 + Mockito

## Version History

| Version | Date | Description |
|---------|------|-------------|
| v0.1.0 | 2026-04-02 | Project initialization |
| v0.2.0 | 2026-04-02 | Bytecode interpreter |
| v0.3.0 | 2026-04-02 | Serial/Parallel GC |
| v0.4.0 | 2026-04-02 | JIT compiler |
| v0.5.0 | 2026-04-02 | Native interface + tools |

## License

This project is licensed under the GNU General Public License v2.0 (GPL-2.0).

See [LICENSE](LICENSE) and [NOTICE](NOTICE) for details.

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'feat: add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Acknowledgments

- [OpenJDK Project](https://openjdk.org/) - Java specification reference
- [Apache Maven](https://maven.apache.org/) - Build system
- [JUnit 5](https://junit.org/junit5/) - Testing framework
- [Mockito](https://site.mockito.org/) - Mocking framework

---

**Mingsha JVM** - A pure Java implementation of Java Virtual Machine
