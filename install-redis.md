# Redis Windows 安装说明

## 方法一：使用 winget（推荐，最简单）

打开 PowerShell（管理员），运行：
```powershell
winget install RedisLabs.Redis
```

安装完成后，Redis 服务会自动启动。

## 方法二：手动下载（如果 winget 失败）

1. 下载地址：https://github.com/tporadowski/redis/releases
2. 下载 `Redis-x64-5.0.14.1.msi` 或最新版本
3. 双击安装，一路下一步

## 验证 Redis 是否安装成功

打开 PowerShell，运行：
```powershell
redis-cli ping
```

如果返回 `PONG`，说明安装成功！

## 启动 Redis（如果服务没自动启动）

```powershell
redis-server
```

---

## 第二步：安装 IntelliJ IDEA

1. 下载地址：https://www.jetbrains.com/idea/download/
2. 下载 **Community** 免费版（够用了）
3. 安装时建议勾选：
   - Create Desktop Shortcut
   - .java 文件关联

## 第三步：安装 Postman

1. 下载地址：https://www.postman.com/downloads/
2. 下载 Windows 版本
3. 安装后注册账号（或跳过）

---

安装好这三样后告诉我，继续下一步！
