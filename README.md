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

### 4. 启动成功标志

控制台出现以下信息，说明项目启动成功：

- `Tomcat started on port(s): 8080`
- `Started SpringbootDemoApplication`

## 数据库初始化

项目已包含初始化脚本：

```text
src/main/resources/sql/init.sql
```

可在 MySQL 中执行该脚本完成建表和初始化数据。

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

## 作者

个人课程作业 / Spring Boot 后端 Demo
