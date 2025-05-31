# Spring Boot Security JWT 集成项目

![Java](https://img.shields.io/badge/Java-17-red?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen?logo=spring)
![Security](https://img.shields.io/badge/Security-6.1.5-yellowgreen)
![Swagger](https://img.shields.io/badge/Swagger-3.0-blueviolet?logo=swagger)
![MyBatis-Plus](https://img.shields.io/badge/MyBatis--Plus-3.5.5-orange)
![License](https://img.shields.io/badge/License-MIT-green)

一个集成了 Spring Security、JWT 认证和 Swagger 文档的 Spring Boot 项目模板。

## 📌 技术栈

- **核心框架**: Spring Boot 3.5.0
- **安全框架**: Spring Security 6 + JWT
- **数据库**: MySQL + MyBatis-Plus 3.5.5
- **API文档**: SpringDoc OpenAPI 2.3.0
- **开发工具**: JDK 17 + Lombok

## 📌 功能特性

- ✅ **JWT 认证**
   - 基于 Token 的无状态认证
   - 自定义 Token 过期时间

- 🔒 **Spring Security 整合**
   - 角色/权限控制
   - CSRF 防护
   - 密码加密存储

- 📚 **Swagger 文档**
   - 自动生成 API 文档
   - JWT 认证头支持
   - 接口分组展示

- 🛠 **其他功能**
   - 统一异常处理（未实现）
   - 自定义响应格式
   - 日志记录（未实现）


## 🚀 快速启动

### 环境要求
- JDK 17 (必须)
- MySQL 8.0+
- Maven 3.6+

### 配置步骤

1. **克隆仓库**
   ```bash
   git clone https://github.com/dhy145899/spring-security-jwt-swagger.git