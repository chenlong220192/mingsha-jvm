# 🚀 Mingsha JVM

![Version](https://img.shields.io/badge/version-1.0.0--SNAPSHOT-blue.svg)
![Java](https://img.shields.io/badge/java-17-orange.svg)
![License](https://img.shields.io/badge/license-GPL--2.0-green.svg)

> 📦 A pure Java implementation of Java Virtual Machine targeting Java 17 specification

---

## 📋 Overview

**Mingsha JVM** (mingsha JVM) is a professional-grade Java Virtual Machine implementation written in pure Java. It is designed to demonstrate core JVM concepts while maintaining production-quality code standards.

---

## ✨ Features

| Feature | Description |
|---------|-------------|
| 🔧 **Multi-module Architecture** | 10 independent Maven modules for different JVM components |
| 📦 **Complete Class Loading** | Bootstrap, Extension, and Application ClassLoaders with parent delegation |
| 💾 **Runtime Data Areas** | Heap, Stack, Method Area, PC Register implementation |
| ⚙️ **Bytecode Interpreter** | Loop-based interpreter with full instruction set support |
| 🧹 **Garbage Collection** | Serial and Parallel GC implementations |
| 🚀 **JIT Compiler** | Hot spot detection and compilation cache |
| 🔌 **Native Interface** | JNI simulation for core Java classes |
| 🛠️ **Diagnostic Tools** | jps, jstack, jmap, jinfo utilities |
| 🧪 **Comprehensive Testing** | JUnit 5 with 100+ unit tests |

---

## 📦 Modules

| Module | Description |
|--------|-------------|
| `mingsha-jvm-core` | ✅ Core constants, Oop model, utilities |
| `mingsha-jvm-classloader` | ✅ ClassLoader implementation with parent delegation |
| `mingsha-jvm-runtime` | ✅ Runtime data areas (heap, stack, method area) |
| `mingsha-jvm-interpreter` | ✅ Bytecode interpreter with instruction set |
| `mingsha-jvm-jit` | ✅ Hot spot detection and JIT compilation |
| `mingsha-jvm-gc` | ✅ Serial and Parallel garbage collectors |
| `mingsha-jvm-native` | ✅ JNI simulation implementation |
| `mingsha-jvm-tools` | ✅ Diagnostic tools (jps, jstack, jmap, jinfo) |
| `mingsha-jvm-boot` | ✅ JVM bootstrap and main entry point |
| `mingsha-jvm-assembly` | ✅ Distribution packaging configuration |

---

## 📌 Requirements

| Requirement | Version |
|-------------|--------|
| **JDK** | 17.0.1 or higher ✅ |
| **Maven** | 3.9.9+ ✅ (Maven Wrapper included) |

---

## 🚀 Quick Start

### 🔨 Build

```bash
# Clone the repository
git clone git@github.com:chenlong220192/mingsha-jvm.git
cd mingsha-jvm

# Build the project
make compile

# Or use Maven directly
./mvnw clean compile
```

### 🧪 Run Tests

```bash
# Run all unit tests
make test

# Run L4 test suite
make test-l4
```

### 📦 Create Distribution Package

```bash
# Build distribution package
make package

# Output will be in target/
ls -la target/*.zip target/*.tar.gz
```

### 🎉 Run HelloWorld

```bash
# Extract distribution
cd target/mingsha-jvm-*/
./bin/java HelloWorld
```

---

## 📁 Project Structure

```
mingsha-jvm/
├── pom.xml                    # ✅ Parent POM
├── Makefile                   # ✅ Build system
├── mvnw                       # ✅ Maven Wrapper
├── .gitignore                 # ✅ Git ignore
├── README.md                  # ✅ Project documentation
├── NOTICE                     # ✅ Third-party licenses
├── LICENSE                    # ✅ GPL-2.0 License
│
├── mingsha-jvm-core/         # ✅ Core module
├── mingsha-jvm-classloader/  # ✅ ClassLoader module
├── mingsha-jvm-runtime/      # ✅ Runtime module
├── mingsha-jvm-interpreter/  # ✅ Interpreter module
├── mingsha-jvm-jit/         # ✅ JIT module
├── mingsha-jvm-gc/          # ✅ GC module
├── mingsha-jvm-native/       # ✅ Native module
├── mingsha-jvm-tools/       # ✅ Tools module
├── mingsha-jvm-boot/        # ✅ Boot module
└── mingsha-jvm-assembly/    # ✅ Assembly module
```

---

## 📦 Distribution Structure

After running `make package`, the distribution will be created:

```
mingsha-jvm-1.0.0-SNAPSHOT/
├── bin/
│   └── java                   # ✅ JVM launcher
├── boot/
│   └── mingsha-jvm-boot-*.jar
├── conf/
│   ├── jvm.properties         # ✅ JVM configuration
│   └── log.properties         # ✅ Logging configuration
├── lib/
│   ├── mingsha-jvm-core-*.jar        # ✅
│   ├── mingsha-jvm-classloader-*.jar # ✅
│   ├── mingsha-jvm-runtime-*.jar     # ✅
│   ├── mingsha-jvm-interpreter-*.jar  # ✅
│   ├── mingsha-jvm-jit-*.jar         # ✅
│   ├── mingsha-jvm-gc-*.jar          # ✅
│   ├── mingsha-jvm-native-*.jar      # ✅
│   ├── mingsha-jvm-tools-*.jar       # ✅
│   └── slf4j-api-*.jar
├── README.md
├── NOTICE
└── LICENSE
```

---

## 🎯 Makefile Targets

| Target | Description | Emoji |
|--------|-------------|-------|
| `make help` | Display this help message | ❓ |
| `make clean` | Clean build artifacts | 🧹 |
| `make compile` | Compile all modules | ⚙️ |
| `make test` | Run unit tests | 🧪 |
| `make test-l4` | Run L4 test suite | 🧪 |
| `make install` | Install to local repository | 📦 |
| `make package` | Build distribution packages | 📦 |
| `make verify` | Full verification (clean + compile + test + package) | ✅ |
| `make hello` | HelloWorld bootstrap test | 🚀 |
| `make run-all-tests` | Run all acceptance tests (L1 + L4) | 🧪 |
| `make quick` | Quick build (clean + compile only) | ⚡ |

---

## 🧪 Testing

### Test Levels

| Level | Type | Status | Count |
|-------|------|--------|-------|
| L1 | Unit Tests | ✅ Passed | 103 |
| L2 | Integration Tests | ✅ Passed | All modules compile |
| L3 | Bootstrap Test | ✅ Passed | HelloWorld executes |
| L4 | Test Suite | ✅ Passed | 5 tests |

### Test Details

| L4 Test | Description | Status |
|---------|-------------|--------|
| Arithmetic | Arithmetic bytecode execution | ✅ |
| Conditional | Conditional branch execution | ✅ |
| Loop | Loop bytecode execution | ✅ |
| MethodCall | Method call bytecode execution | ✅ |
| FieldAccess | Field access bytecode execution | ✅ |

**📊 Total: 108 tests (103 unit + 5 L4) - All Passed ✅**

---

## 📜 License

This project is licensed under the **GNU General Public License v2.0 (GPL-2.0)** ✅

See [LICENSE](LICENSE) and [NOTICE](NOTICE) for details.

---

## 🤝 Contributing

1. 🍴 Fork the repository
2. 🌿 Create a feature branch (`git checkout -b feature/amazing-feature`)
3. 📝 Commit your changes (`git commit -m 'feat: add amazing feature'`)
4. ⬆️ Push to the branch (`git push origin feature/amazing-feature`)
5. 🎉 Open a Pull Request

---

<div align="center">

**🚀 Mingsha JVM** - A pure Java implementation of Java Virtual Machine

*Built with ❤️ by mingsha team*

</div>
