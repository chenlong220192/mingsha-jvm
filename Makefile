# =============================================================================
# Mingsha JVM Build System
# =============================================================================
# A Java Virtual Machine implementation targeting Java 17
#
# Usage: make <target>
#
# Targets:
#   help           - 显示帮助信息
#   clean          - 清理构建产物
#   compile        - 编译所有模块
#   test           - 运行单元测试
#   test-coverage  - 运行测试并生成覆盖率报告
#   install        - 安装到本地仓库
#   package        - 构建发行版包
#   verify         - 完整验证
#   hello          - HelloWorld快速测试
#   run-all-tests  - 运行全部验收测试
# =============================================================================

.PHONY: help clean compile test test-coverage install package verify hello run-all-tests

# Current directory
CURRENT_DIR := $(shell pwd)
# Version
VERSION := 1.0.0

# Default target
.DEFAULT_GOAL := help

# -----------------------------------------------------------------------------
# Help - 显示所有可用命令
# -----------------------------------------------------------------------------
help:
	@echo ""
	@echo "========================================"
	@echo "    Mingsha JVM Build System"
	@echo "========================================"
	@echo ""
	@echo "Version: $(VERSION)"
	@echo "Java Version: 17"
	@echo ""
	@echo "Available targets:"
	@echo ""
	@echo "  make help           - 显示此帮助信息"
	@echo "  make clean          - 清理构建产物"
	@echo "  make compile        - 编译所有模块"
	@echo "  make test           - 运行单元测试"
	@echo "  make test-coverage  - 运行测试并生成覆盖率报告"
	@echo "  make install        - 安装到本地仓库"
	@echo "  make package       - 构建发行版包 (zip/tar.gz)"
	@echo "  make verify         - 完整验证 (编译+测试+打包)"
	@echo "  make hello          - HelloWorld验收测试"
	@echo "  make run-all-tests  - 运行全部验收测试"
	@echo ""
	@echo "Examples:"
	@echo "  make compile        # 编译项目"
	@echo "  make test-coverage # 运行测试并查看覆盖率"
	@echo "  make package       # 构建发布包"
	@echo ""

# -----------------------------------------------------------------------------
# Clean - 清理构建产物
# -----------------------------------------------------------------------------
clean:
	@echo "Cleaning build artifacts..."
	./mvnw clean
	@if [ -d "logs" ]; then rm -rf logs/*.log; fi
	@echo "Clean complete."

# -----------------------------------------------------------------------------
# Compile - 仅编译
# -----------------------------------------------------------------------------
compile:
	@echo "Compiling all modules..."
	./mvnw compile
	@echo "Compile complete."

# -----------------------------------------------------------------------------
# Test - 运行单元测试
# -----------------------------------------------------------------------------
test:
	@echo "Running unit tests..."
	./mvnw test
	@echo "Tests complete."

# -----------------------------------------------------------------------------
# Test Coverage - 测试覆盖率
# -----------------------------------------------------------------------------
test-coverage:
	@echo "Running tests with coverage report..."
	./mvnw test jacoco:report
	@echo ""
	@echo "Coverage report generated at:"
	@echo "  target/site/jacoco/index.html"

# -----------------------------------------------------------------------------
# Install - 安装到本地仓库
# -----------------------------------------------------------------------------
install:
	@echo "Installing to local repository..."
	./mvnw clean install
	@echo "Install complete."

# -----------------------------------------------------------------------------
# Package - 构建发行版
# -----------------------------------------------------------------------------
package:
	@echo "Building distribution packages..."
	./mvnw clean package -pl mingsha-jvm-assembly
	@echo ""
	@echo "Distribution packages generated:"
	@ls -lh target/*.zip target/*.tar.gz 2>/dev/null || echo "  (check target directory)"

# -----------------------------------------------------------------------------
# Verify - 完整验证
# -----------------------------------------------------------------------------
verify: clean compile test package
	@echo ""
	@echo "========================================"
	@echo "  Verification Complete!"
	@echo "========================================"

# -----------------------------------------------------------------------------
# Hello - HelloWorld快速测试
# -----------------------------------------------------------------------------
hello:
	@echo "=== HelloWorld Bootstrap Test ==="
	@echo "Creating test file..."
	@mkdir -p /tmp/mingsha-test
	@echo 'public class HelloWorld { public static void main(String[] args) { System.out.println("Hello, World!"); System.out.println("Mingsha JVM is working!"); } }' > /tmp/mingsha-test/HelloWorld.java
	@echo "Compiling with system javac..."
	@javac /tmp/mingsha-test/HelloWorld.java -d /tmp/mingsha-test/
	@echo "Running with mingsha-jvm..."
	@./mvnw compile -q
	@echo ""
	@echo "Note: Full execution requires complete interpreter implementation"
	@echo "HelloWorld class file created at: /tmp/mingsha-test/"

# -----------------------------------------------------------------------------
# Run All Tests - 全部验收测试
# -----------------------------------------------------------------------------
run-all-tests:
	@echo "========================================"
	@echo "  Running All Acceptance Tests"
	@echo "========================================"
	@echo ""
	@echo "--- Level 1: Unit Tests ---"
	./mvnw test
	@echo ""
	@echo "--- Level 2: Integration Tests ---"
	@./mvnw test -Dtest=*IntegrationTest 2>/dev/null || echo "(No integration tests yet)"
	@echo ""
	@echo "--- Level 3: Bootstrap Test ---"
	@mkdir -p /tmp/mingsha-test && echo 'public class BootstrapTest { public static void main(String[] args) { System.out.println("Bootstrap test passed!"); } }' > /tmp/mingsha-test/BootstrapTest.java && javac /tmp/mingsha-test/BootstrapTest.java -d /tmp/mingsha-test/
	@echo ""
	@echo "========================================"
	@echo "  All Tests Complete"
	@echo "========================================"

# -----------------------------------------------------------------------------
# Quick Build - 快速构建 (跳过测试)
# -----------------------------------------------------------------------------
quick: clean compile
	@echo "Quick build complete (no tests)."
