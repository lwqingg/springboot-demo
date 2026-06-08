# Spring Boot Demo

一个基于 **Spring Boot 2.7.18 + MyBatis + MySQL + Redis** 的后端小 Demo，采用前后端分离思想，仅提供 RESTful 接口，不包含前端页面。

## 项目简介

本项目用于课程汇报与现场演示，核心目标是独立搭建并运行一个可用的 Spring Boot 后端服务。

已实现功能：

- 用户信息 CRUD
- 从 MySQL 查询数据并写入 Redis
- 从 Redis 读取缓存数据
- 使用 Postman / Apifox 调试接口

## 技术栈

- JDK 8
- Spring Boot 2.7.18
- MyBatis 2.3.2
- MySQL 5.7.44
- Redis
- Maven
- IntelliJ IDEA

## 功能接口

### 1. 用户 CRUD

| 方法 | 接口 | 说明 |
|------|------|------|
| GET | `/api/users/{id}` | 根据 ID 查询用户 |
| GET | `/api/users/list` | 查询所有用户 |
| POST | `/api/users` | 新增用户 |
| PUT | `/api/users/{id}` | 修改用户 |
| DELETE | `/api/users/{id}` | 删除用户 |

### 2. Redis 相关接口

| 方法 | 接口 | 说明 |
|------|------|------|
| GET | `/api/users/{id}/cache` | 从 MySQL 读取并写入 Redis |
| GET | `/api/users/{id}/redis` | 从 Redis 读取用户缓存 |

### 3. 登录接口

| 方法 | 接口 | 说明 |
|------|------|------|
| POST | `/api/users/login` | 用户登录并写入 Redis |

## 运行环境

- Windows 10
- JDK 8
- MySQL 5.7（已配置为 Windows 服务）
- Redis（手动启动或配置为服务均可）
- IntelliJ IDEA

## 启动方式

### 1. 启动 MySQL

本项目的 MySQL 已配置为 **Windows 服务**，可直接启动服务：

```powershell
net start MySQL57
```

如果需要停止服务：

```powershell
net stop MySQL57
```

### 2. 启动 Redis

如果 Redis 没有配置为服务，可手动启动：

```powershell
& "D:\Docker\Docker\Redis\redis-server.exe"
```

如需带配置文件启动：

```powershell
& "D:\Docker\Docker\Redis\redis-server.exe" "D:\Docker\Docker\Redis\redis.windows.conf"
```

### 3. 在 IDEA 中运行项目

使用 IntelliJ IDEA 打开项目根目录：

```text
springboot-demo
```

找到启动类：

```text
src/main/java/com.demo/SpringbootDemoApplication.java
```

点击运行即可。

项目默认端口：

```text
8080
```

### 启动成功标志

控制台出现以下信息，说明项目启动成功：

- `Tomcat started on port(s): 8080`
- `Started SpringbootDemoApplication`

### 数据库初始化

项目已包含初始化脚本：

```text
src/main/resources/sql/init.sql
```

可在 MySQL 中执行该脚本完成建表和初始化数据。


## 项目结构说明

本项目采用标准的 Spring Boot 分层架构，整体结构清晰，便于后续维护和扩展。主要分为启动层、控制层、业务层、持久层、实体层、配置层以及数据库初始化脚本等部分。

### 启动类

启动类位于：

```text
src/main/java/com/demo/SpringbootDemoApplication.java
```

该类是整个项目的入口类，负责启动 Spring Boot 应用。项目运行时会先加载该启动类，并初始化 Spring 容器、内嵌 Tomcat、MyBatis、Redis 等相关组件。

**作用说明：**

- 作为 Spring Boot 程序入口
- 启动整个后端服务
- 完成自动配置与组件扫描

---

### Controller 层

控制层位于：

```text
src/main/java/com/demo/controller/UserController.java
```

Controller 层主要负责接收前端请求，定义 RESTful 风格接口，并将请求参数传递给 Service 层处理。本项目中的用户相关接口，包括查询、新增、修改、删除，以及 Redis 相关接口，均在该层统一对外提供。

**作用说明：**

- 接收前端请求
- 处理请求参数
- 返回统一格式的响应结果
- 不直接编写复杂业务逻辑

**接口示例：**

- 查询用户
- 查询用户列表
- 新增用户
- 修改用户
- 删除用户
- MySQL 写入 Redis
- 从 Redis 读取数据

**设计原因：**

Controller 是 Web 层的入口，主要负责请求分发和参数接收，便于接口管理和后续扩展。

---

### Service 层

业务逻辑层位于：

```text
src/main/java/com/demo/service/UserService.java
```

Service 层主要负责具体的业务处理逻辑，是 Controller 和 Mapper 之间的桥梁。在本项目中，Service 层负责调用 Mapper 层访问数据库，同时处理 Redis 缓存相关逻辑，例如读取 MySQL 数据后写入 Redis，或者直接从 Redis 中读取缓存数据。

**作用说明：**

- 编写核心业务逻辑
- 调用数据库访问层
- 处理缓存读写逻辑
- 保持 Controller 层简洁

**设计优势：**

- 分层清晰
- 可读性高
- 后期维护和扩展方便

---

### Mapper 层

持久层位于：

```text
src/main/java/com/demo/mapper/UserMapper.java
```

Mapper 层主要负责与数据库进行交互，MyBatis 会通过该接口执行 SQL 语句，实现对用户表的增删改查操作。

**作用说明：**

- 执行数据库相关 SQL
- 完成用户数据查询与持久化
- 与 MySQL 直接交互

**设计原因：**

使用 MyBatis 可以让 SQL 与 Java 代码分离，SQL 更加清晰可控，相比直接编写 JDBC 代码更容易维护。

---

### 实体类

实体类位于：

```text
src/main/java/com/demo/entity/User.java
```

实体类用于封装用户信息，并与数据库中的用户表字段进行映射。项目中查询到的数据会转换为 `User` 对象，方便在 Java 代码中进行操作和传递。

**作用说明：**

- 封装用户数据
- 对应数据库表结构
- 便于对象和数据库数据之间的转换

**常见字段：**

- id
- username
- password
- nickname
- email
- status

---

### 配置文件

项目配置文件位于：

```text
src/main/resources/application.yml
```

该文件用于配置项目运行所需的基础信息，包括数据库连接、Redis 连接、服务端口以及 MyBatis 配置等。

**主要配置内容：**

- 服务器端口
- MySQL 数据源
- Redis 连接地址和端口
- MyBatis 配置
- 日志输出配置

**作用说明：**

- 管理项目运行环境
- 连接数据库和缓存服务
- 提供统一配置入口

---

### SQL 初始化脚本

数据库初始化脚本位于：

```text
src/main/resources/sql/init.sql
```

该文件用于数据库建表和初始化数据，方便快速部署项目环境。在实际使用时，可以先在 MySQL 中执行该脚本，完成用户表的创建以及测试数据的插入。

**执行方式：**

如果在 MySQL 客户端中执行，可以使用：

```sql
source src/main/resources/sql/init.sql
```

## 接口测试示例

### 查询所有用户

```http
GET http://localhost:8080/api/users/list
```

### 查询单个用户

```http
GET http://localhost:8080/api/users/1
```

### 新增用户

```http
POST http://localhost:8080/api/users
Content-Type: application/json

{
  "username": "newuser",
  "password": "123456",
  "nickname": "新用户",
  "email": "new@demo.com",
  "status": 1
}
```

### 修改用户

```http
PUT http://localhost:8080/api/users/1
Content-Type: application/json

{
  "username": "admin",
  "password": "newpassword",
  "nickname": "超级管理员",
  "email": "admin@demo.com",
  "status": 1
}
```

### 删除用户

```http
DELETE http://localhost:8080/api/users/3
```

### MySQL 读取并写入 Redis

```http
GET http://localhost:8080/api/users/1/cache
```

### 从 Redis 读取

```http
GET http://localhost:8080/api/users/1/redis
```

## 项目结构

```text
springboot-demo/
├── pom.xml
├── src/
│   └── main/
│       ├── java/com/demo/
│       │   ├── SpringbootDemoApplication.java
│       │   ├── config/
│       │   │   └── RedisConfig.java
│       │   ├── controller/
│       │   │   └── UserController.java
│       │   ├── entity/
│       │   │   └── User.java
│       │   ├── mapper/
│       │   │   └── UserMapper.java
│       │   └── service/
│       │       └── UserService.java
│       └── resources/
│           ├── application.yml
│           └── sql/
│               └── init.sql
└── README.md
```

## 常见问题

### 1. Maven 不能识别

请检查 Maven 是否已安装并配置到环境变量中。

### 2. `JAVA_HOME` 报错

请确保已经安装 JDK 8，并正确配置 `JAVA_HOME` 和 `Path`。

### 3. 数据库连接失败

请检查：

- MySQL 服务是否已启动
- 数据库地址、端口、用户名、密码是否正确
- 数据库表是否已创建

### 4. Redis 连接失败

请检查 Redis 是否已启动，默认端口是否为 `6379`。

## 演示说明

本项目适合现场演示以下内容：

1. 项目启动成功
2. 数据库表结构与初始化脚本
3. 用户 CRUD 接口
4. MySQL 写入 Redis
5. Redis 读取缓存
6. 使用 Postman / Apifox 完成接口验证

### lwqing

个人项目demo / Spring Boot 后端 Demo
