create database securitydb;
use securitydb;
-- 创建 users 表
CREATE TABLE users
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- 创建 roles 表
CREATE TABLE roles
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    uid  INT         NOT NULL,
    role VARCHAR(50) NOT NULL,
    FOREIGN KEY (uid) REFERENCES users (id) ON DELETE CASCADE
);