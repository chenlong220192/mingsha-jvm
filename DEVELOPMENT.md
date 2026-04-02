# Mingsha JVM Development Documentation

## 项目概述

**项目名称**: mingsha-jvm
**类型**: Java Virtual Machine 实现
**目标版本**: Java 17
**构建工具**: Maven 3.9+ with Maven Wrapper
**仓库**: git@github.com:chenlong220192/mingsha-jvm.git

---

## 开发进度

### 2026-04-02: v1.0.0 完整版发布

#### 已完成
- [x] 文档完善 (README, NOTICE, DEVELOPMENT.md, Makefile)
- [x] 10 个 Maven 模块实现
- [x] 所有模块 Javadoc + SLF4J 日志
- [x] 单元测试 (103 tests)
- [x] 集成测试
- [x] HelloWorld 自举测试 (输出 "Hello, World!")
- [x] 打包制品 (zip/tar.gz)

---

## 模块列表

| 模块 | artifactId | 用途 |
|------|------------|------|
| mingsha-jvm-core | 核心模块 | 常量、Oop模型、工具类 |
| mingsha-jvm-classloader | 类加载器 | 双亲委派模型 |
| mingsha-jvm-runtime | 运行时 | 堆、栈、方法区、线程 |
| mingsha-jvm-interpreter | 解释器 | 字节码执行 |
| mingsha-jvm-jit | JIT | 热点检测 |
| mingsha-jvm-gc | GC | 垃圾回收器 |
| mingsha-jvm-native | Native | JNI模拟 |
| mingsha-jvm-tools | 工具 | jps/jstack/jmap/jinfo |
| mingsha-jvm-boot | 启动器 | Main入口 |
| mingsha-jvm-assembly | 打包 | 制品生成 |

---

## 验收测试状态

| 级别 | 状态 | 说明 |
|------|------|------|
| L1: 单元测试 | ✅ | 103 tests passing |
| L2: 集成测试 | ✅ | All modules compile |
| L3: HelloWorld | ✅ | 输出 "Hello, World!" |
| L4: 测试套件 | ⏳ | 待完整字节码支持 |

---

## 项目结构

```
mingsha-jvm/
├── pom.xml                    # Parent POM
├── Makefile                   # 构建脚本
├── mvnw                       # Maven Wrapper
├── .gitignore
├── README.md
├── NOTICE
├── DEVELOPMENT.md
│
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

---

## 制品包结构

```
mingsha-jvm-1.0.0-SNAPSHOT/
├── bin/
│   └── java                   # 启动脚本
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

---

## 构建命令

```bash
# 显示帮助
make help

# 编译
make compile

# 测试
make test

# 打包
make package

# 验证
make verify

# HelloWorld测试
make hello
```

---

## Git 提交历史

```
a544973 docs: Update DEVELOPMENT.md - L3 HelloWorld complete
8b28c0a feat(boot): Add HelloWorld bootstrap execution support
e76617b fix(jvm): Correct JVM bytecode constants and extend interpreter
79703f0 docs: Update DEVELOPMENT.md with progress
315ce1e test(all): Add JUnit 5 unit tests for all modules
1da97c6 test(core): Add JUnit 5 tests for Core module
03f5a52 refactor(boot): Enhance boot module with logging
40e000b refactor(gc/jit/native/tools): Enhance modules with Javadoc and logging
4e43772 refactor(interpreter): Enhance LoopInterpreter with logging
03b0e53 refactor(classloader): Enhance ClassLoader module with Javadoc and logging
1320c81 refactor(core/runtime): Enhance code quality with Javadoc and logging
9441d11 docs: Update documentation - README, NOTICE, DEVELOPMENT.md, Makefile
```

---

## Git 工作流

### 分支策略
- `main` - 主分支
- `develop` - 开发分支
- `feature/<name>` - 特性分支

### Tag 格式
- `v1.0.0` - 正式版

### Commit 格式
```
<type>(<scope>): <subject>
```

Types: feat, fix, docs, style, refactor, test, chore

---

## 联系

项目: https://github.com/chenlong220192/mingsha-jvm
