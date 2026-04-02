# Mingsha JVM Development Documentation

## 项目概述

**项目名称**: mingsha-jvm
**类型**: Java Virtual Machine 实现
**目标版本**: Java 17
**构建工具**: Maven 3.9+ with Maven Wrapper
**仓库**: git@github.com:chenlong220192/mingsha-jvm.git

---

## 开发进度日志

### 2026-04-02: 项目初始化

#### 已完成
- [x] 项目结构设计 (9个Maven模块)
- [x] Parent POM 创建
- [x] Maven Wrapper 配置
- [x] .gitignore 创建
- [x] Makefile 创建 (含help/clean/test等目标)

#### 模块列表
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

## TODO List (任务清单)

### 阶段1: 文档完善
- [ ] 重写 README.md (完整项目文档)
- [ ] 完善 NOTICE (第三方依赖)
- [ ] 创建 DEVELOPMENT.md (本文件)

### 阶段2: Core 模块增强
- [ ] JVMConstants 添加所有常量
- [ ] OopDesc 类层次完善
- [ ] BytecodeReader 完整实现
- [ ] 添加Javadoc注释
- [ ] 添加slf4j日志

### 阶段3: ClassLoader 模块完善
- [ ] MingshaClassLoader 完整实现
- [ ] 双亲委派模型
- [ ] BootstrapClassLoader 实现
- [ ] ExtensionClassLoader 实现
- [ ] AppClassLoader 实现

### 阶段4: Runtime 模块完善
- [ ] HeapSpace 完整内存管理
- [ ] YoungGen 年轻代实现
- [ ] MethodArea 类元数据管理
- [ ] JavaStack 栈帧管理
- [ ] MingshaThread 线程管理

### 阶段5: Interpreter 完整实现
- [ ] 完整字节码指令 (~200条)
- [ ] loads/stores/arithmetic/control/return/invoke

### 阶段6: GC 模块完善
- [ ] SerialGC 完整实现
- [ ] ParallelGC 完整实现
- [ ] GCRoots 可达性分析

### 阶段7: JIT 模块完善
- [ ] HotSpotDetector 热点检测
- [ ] JITCompiler 编译缓存

### 阶段8: Native 模块完善
- [ ] JNIBridge JNI模拟
- [ ] java.lang.Object 本地方法
- [ ] java.lang.System 本地方法

### 阶段9: Tools 模块完善
- [ ] JpsTool 进程状态
- [ ] JStackTool 线程栈
- [ ] JMapTool 内存映射
- [ ] JInfoTool JVM信息

### 阶段10: 打包配置完善
- [ ] assembly/bin.xml
- [ ] bin/ 启动脚本
- [ ] conf/ 配置文件

### 阶段11: 单元测试
- [ ] 配置 surefire plugin
- [ ] JUnit 5 + Mockito
- [ ] 覆盖率目标: 90%+

### 阶段12: 验收测试
- [ ] L1: 单元测试
- [ ] L2: 集成测试
- [ ] L3: 自举测试 (HelloWorld)
- [ ] L4: 测试套件

---

## 项目结构

```
mingsha-jvm/
├── pom.xml                                     # Parent POM
├── Makefile                                    # 构建脚本
├── .gitignore                                 # Git忽略配置
│
├── mingsha-jvm-core/                          # 核心模块
│   └── src/main/java/com/mingsha/jvm/core/
│       ├── MingshaVMVersion.java
│       ├── MingshaVMProperties.java
│       ├── constants/JVMConstants.java
│       ├── oop/{OopDesc,InstanceOop,ArrayOop...}
│       └── utils/BytecodeReader.java
│
├── mingsha-jvm-classloader/                   # 类加载器
│   └── src/main/java/com/mingsha/jvm/classloader/
│       ├── MingshaClassLoader.java
│       ├── BootstrapClassLoader.java
│       ├── ExtensionClassLoader.java
│       └── AppClassLoader.java
│
├── mingsha-jvm-runtime/                       # 运行时
│   └── src/main/java/com/mingsha/jvm/runtime/
│       ├── heap/{HeapSpace,YoungGen,ObjectHeader}
│       ├── stack/{JavaStack,StackFrame}
│       ├── methodarea/{MethodArea,KlassModel}
│       ├── thread/MingshaThread.java
│       └── pc/PCRegister.java
│
├── mingsha-jvm-interpreter/                   # 解释器
│   └── src/main/java/com/mingsha/jvm/interpreter/
│       ├── LoopInterpreter.java
│       └── instructions/
│
├── mingsha-jvm-jit/                           # JIT
├── mingsha-jvm-gc/                            # GC
├── mingsha-jvm-native/                        # Native
├── mingsha-jvm-tools/                         # Tools
├── mingsha-jvm-boot/                          # Boot
└── mingsha-jvm-assembly/                    # Assembly
    └── src/main/
        ├── assembly/bin.xml
        ├── scripts/
        └── conf/
```

---

## 制品包结构

```
mingsha-jvm-1.0.0/
├── bin/                                       # 可执行脚本
├── boot/                                      # 启动JAR
├── conf/                                      # 配置文件
├── lib/                                       # 运行时类库 (8个模块)
├── logs/                                      # 日志目录
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

# 测试覆盖率
make test-coverage

# 安装
make install

# 打包
make package

# 验证
make verify

# HelloWorld测试
make hello
```

---

## Git 工作流

### 分支策略
- `main` - 主分支
- `develop` - 开发分支
- `feature/<name>` - 特性分支

### Tag 格式
- `v0.1.0` - Phase 1
- `v0.2.0` - Phase 2
- ...
- `v1.0.0` - 正式版

### Commit 格式
```
<type>(<scope>): <subject>

<body>
```

Types: feat, fix, docs, style, refactor, test, chore

---

## 联系

项目: https://github.com/chenlong220192/mingsha-jvm
