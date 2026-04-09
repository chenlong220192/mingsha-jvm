#======================================================================
#
# mingsha-jvm Build System
#
# A pure Java implementation of Java Virtual Machine targeting Java 17
#
# Usage: make <target> [SKIP_TEST=true|false]
#
# author: mingsha
# date: 2026-04-02
#======================================================================

SHELL := /bin/bash -o pipefail

export BASE_PATH := $(shell dirname $(realpath $(lastword $(MAKEFILE_LIST))))

# ----------------------------- colors <-----------------------------
ROCKET := 🚀
GEAR := ⚙️
TEST := 🧪
PACKAGE := 📦
CLEAN := 🧹
HELP := ❓
INFO := ℹ️
SUCCESS := ✅
WARNING := ⚠️
ERROR := ❌

RED=\033[31m
GREEN=\033[32m
YELLOW=\033[33m
BLUE=\033[34m
CYAN=\033[36m
BOLD=\033[1m
RESET=\033[0m
# ----------------------------- colors >-----------------------------

# ----------------------------- variables <-----------------------------
SKIP_TEST ?= false
VERSION := 1.0.0
# ----------------------------- variables >-----------------------------

# ----------------------------- help <-----------------------------
.PHONY: help
help: ## ❓ 显示帮助信息
	@printf "${BOLD}${CYAN}╔════════════════════════════════════════════════════════════════╗${RESET}\n"
	@printf "${BOLD}${CYAN}║            ${ROCKET}  mingsha-jvm 构建工具  ${ROCKET}                   ║${RESET}\n"
	@printf "${BOLD}${CYAN}╚════════════════════════════════════════════════════════════════╝${RESET}\n"
	@printf "\n"
	@printf "${BOLD}${YELLOW}%-8s:${RESET}\n" "使用方法"
	@printf "  make <target> [SKIP_TEST=true|false]\n"
	@printf "\n"
	@printf "${BOLD}${YELLOW}%-8s:${RESET}\n" "环境变量"
	@printf "  ${GREEN}%-22s${RESET} %s\n" "SKIP_TEST" "- 跳过测试 (默认: false, 可选: true)"
	@printf "\n"
	@printf "${BOLD}${YELLOW}%-8s:${RESET}\n" "可用目标"
	@awk 'BEGIN {FS = ":.*?## "; max=22} /^[a-zA-Z0-9_.-]+:.*?## / {cmd=$$1; desc=$$2; printf "  ${GREEN}%-*s${RESET} %s\n", max, cmd, desc}' max=22 $(MAKEFILE_LIST) | \
		sed 's/\$$(HELP)/$(HELP)/g' | sed 's/\$$(CLEAN)/$(CLEAN)/g' | sed 's/\$$(TEST)/$(TEST)/g' | sed 's/\$$(PACKAGE)/$(PACKAGE)/g' | sed 's/\$$(GEAR)/$(GEAR)/g' | sed 's/\$$(INFO)/$(INFO)/g' | sed 's/\$$(ROCKET)/$(ROCKET)/g' | sed 's/\$$(SUCCESS)/$(SUCCESS)/g'
	@printf "\n"
	@printf "${BOLD}${YELLOW}%-8s:${RESET}\n" "示例"
	@printf "  ${GREEN}%-22s${RESET} %s\n" "make help" "${HELP} 显示此帮助信息"
	@printf "  ${GREEN}%-22s${RESET} %s\n" "make clean" "${CLEAN} 清理构建文件"
	@printf "  ${GREEN}%-22s${RESET} %s\n" "make compile" "${GEAR} 编译所有模块"
	@printf "  ${GREEN}%-22s${RESET} %s\n" "make test" "${TEST} 运行单元测试"
	@printf "  ${GREEN}%-22s${RESET} %s\n" "make package" "${PACKAGE} 构建发行版包"
	@printf "\n"
	@printf "${BOLD}${CYAN}╔════════════════════════════════════════════════════════════════╗${RESET}\n"
	@printf "${BOLD}${CYAN}║              ${SUCCESS}  构建愉快！${SUCCESS}                                    ║${RESET}\n"
	@printf "${BOLD}${CYAN}╚════════════════════════════════════════════════════════════════╝${RESET}\n"

.DEFAULT_GOAL := help
# ----------------------------- help >-----------------------------

# ----------------------------- build <-----------------------------
clean: ## 🧹 清理构建文件
	@printf "${BLUE}${CLEAN} 清理构建文件...${RESET}\n"
	$(BASE_PATH)/mvnw --batch-mode --errors -f ${BASE_PATH}/pom.xml clean
	@if [ -d "logs" ]; then rm -rf logs/*.log; fi
	@printf "${GREEN}${SUCCESS} 清理完成！${RESET}\n"

compile: ## ⚙️ 编译所有模块
	@printf "${BLUE}${GEAR} 编译所有模块...${RESET}\n"
	$(BASE_PATH)/mvnw --batch-mode --errors -f ${BASE_PATH}/pom.xml compile
	@printf "${GREEN}${SUCCESS} 编译完成！${RESET}\n"

test: ## 🧪 运行单元测试
	@printf "${BLUE}${TEST} 运行单元测试...${RESET}\n"
	$(BASE_PATH)/mvnw --batch-mode --errors --fail-at-end --update-snapshots -f ${BASE_PATH}/pom.xml clean test -D test=*Test -DfailIfNoTests=false
	@printf "${GREEN}${SUCCESS} 测试完成！${RESET}\n"

test-l4: ## 🧪 运行L4测试套件
	@printf "${BLUE}${TEST} 运行L4测试套件...${RESET}\n"
	$(BASE_PATH)/mvnw --batch-mode --errors -f ${BASE_PATH}/pom.xml test -Dtest=L4TestSuite
	@printf "${GREEN}${SUCCESS} L4测试完成！${RESET}\n"

install: ## 📦 安装到本地仓库
	@printf "${BLUE}${PACKAGE} 安装到本地仓库...${RESET}\n"
	$(BASE_PATH)/mvnw --batch-mode --errors -f ${BASE_PATH}/pom.xml clean install -DskipTests=$(SKIP_TEST)
	@printf "${GREEN}${SUCCESS} 安装完成！${RESET}\n"

package: ## 📦 构建发行版包
	@printf "${BLUE}${PACKAGE} 构建发行版包...${RESET}\n"
	$(BASE_PATH)/mvnw --batch-mode --errors --fail-at-end --update-snapshots -f ${BASE_PATH}/pom.xml clean package -DskipTests=$(SKIP_TEST)
	@printf "${BLUE}📁 复制制品到项目根目录...${RESET}\n"
	@mkdir -p ${BASE_PATH}/target
	@cp -f ${BASE_PATH}/assembly/target/mingsha-jvm-2026.04.09-bin.tar.gz ${BASE_PATH}/target/ 2>/dev/null || true
	@cp -f ${BASE_PATH}/assembly/target/mingsha-jvm-2026.04.09-bin.zip ${BASE_PATH}/target/ 2>/dev/null || true
	@printf "\n"
	@printf "${GREEN}${INFO} 发行版包已生成：${RESET}\n"
	@ls -lh ${BASE_PATH}/target/*.tar.gz ${BASE_PATH}/target/*.zip 2>/dev/null || echo "  (请检查 target 目录)"
	@printf "${GREEN}${SUCCESS} 构建完成！${RESET}\n"

verify: clean compile test package ## ✅ 完整验证
	@printf "\n"
	@printf "${BOLD}${GREEN}╔════════════════════════════════════════════════════════════════╗${RESET}\n"
	@printf "${BOLD}${GREEN}║                    ${SUCCESS} 验证完成！${SUCCESS}                              ║${RESET}\n"
	@printf "${BOLD}${GREEN}╚════════════════════════════════════════════════════════════════╝${RESET}\n"

hello: ## 🚀 HelloWorld快速测试
	@printf "${BLUE}${ROCKET} HelloWorld快速测试...${RESET}\n"
	@mkdir -p /tmp/mingsha-test
	@echo 'public class HelloWorld { public static void main(String[] args) { System.out.println("Hello, World!"); System.out.println("mingsha-jvm is working!"); } }' > /tmp/mingsha-test/HelloWorld.java
	@cd /tmp/mingsha-test && javac HelloWorld.java 2>/dev/null && echo "Java编译成功" || echo "Java编译失败"
	@printf "${GREEN}${SUCCESS} HelloWorld测试完成！${RESET}\n"

run-all-tests: ## 🧪 运行全部验收测试
	@printf "${BOLD}${CYAN}╔════════════════════════════════════════════════════════════════╗${RESET}\n"
	@printf "${BOLD}${CYAN}║                  ${TEST}  运行全部验收测试  ${TEST}                       ║${RESET}\n"
	@printf "${BOLD}${CYAN}╚════════════════════════════════════════════════════════════════╝${RESET}\n"
	@printf "\n"
	@printf "${BOLD}${YELLOW}--- Level 1: 单元测试 ---${RESET}\n"
	$(BASE_PATH)/mvnw --batch-mode -f ${BASE_PATH}/pom.xml test
	@printf "\n"
	@printf "${BOLD}${YELLOW}--- Level 4: L4测试套件 ---${RESET}\n"
	$(BASE_PATH)/mvnw --batch-mode -f ${BASE_PATH}/pom.xml test -Dtest=L4TestSuite
	@printf "\n"
	@printf "${BOLD}${GREEN}╔════════════════════════════════════════════════════════════════╗${RESET}\n"
	@printf "${BOLD}${GREEN}║                  ${SUCCESS} 全部测试完成！${SUCCESS}                             ║${RESET}\n"
	@printf "${BOLD}${GREEN}╚════════════════════════════════════════════════════════════════╝${RESET}\n"

quick: clean compile ## ⚡ 快速构建
	@printf "${GREEN}${SUCCESS} 快速构建完成 (跳过测试)${RESET}\n"
# ----------------------------- build >-----------------------------
