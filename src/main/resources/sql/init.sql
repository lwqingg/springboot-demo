-- 创建数据库
CREATE DATABASE IF NOT EXISTS springboot_demo DEFAULT CHARACTER SET utf8mb4;

-- 使用数据库
USE springboot_demo;

-- 创建用户表
DROP TABLE IF EXISTS user;
CREATE TABLE user (
    id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    email VARCHAR(100),
    status TINYINT(1) DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入测试数据
INSERT INTO user (username, password, nickname, email, status) VALUES ('admin', '123456', 'admin', 'admin@demo.com', 1);
INSERT INTO user (username, password, nickname, email, status) VALUES ('test', '123456', 'test', 'test@demo.com', 1);

-- 验证
SELECT * FROM user;
