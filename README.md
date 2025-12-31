# Spring Boot 论坛/博客系统

一个基于Spring Boot的论坛/博客系统，使用Spring MVC架构和Thymeleaf模板引擎。
http://10.100.164.5:8080/SpringDemo01-0.0.1-SNAPSHOT/
## 项目特性

- 用户注册与登录系统
- 文章发布与管理
- 评论与回复功能（支持嵌套回复）
- 会话管理与登录验证
- 验证码功能
- 响应式前端界面

## 技术栈

- **框架**: Spring Boot 3.5.9
- **语言**: Java 17
- **模板引擎**: Thymeleaf
- **构建工具**: Maven
- **数据库**: MySQL
- **打包方式**: WAR（可部署到外部Tomcat服务器）

## 项目结构

```
src/
├── main/
│   ├── java/
│   │   └── org/example/springdemo01/
│   │       ├── config/          # 配置类
│   │       ├── controller/      # 控制器层
│   │       ├── entity/          # 实体类
│   │       ├── exception/       # 异常处理
│   │       ├── intercept/       # 拦截器
│   │       ├── service/         # 服务接口
│   │       │   └── impl/        # 服务实现
│   │       ├── util/            # 工具类
│   │       ├── ServletInitializer.java  # Servlet初始化器
│   │       └── SpringDemo01Application.java  # 应用主类
│   └── resources/
│       ├── static/              # 静态资源（CSS, JS）
│       ├── templates/           # Thymeleaf模板
│       └── application.properties  # 应用配置
```

## 功能模块

### 1. 用户模块
- 用户注册
- 用户登录/登出
- 验证码验证

### 2. 文章模块
- 文章列表展示
- 创建新文章
- 文章详情查看

### 3. 评论模块
- 添加评论
- 回复评论（支持嵌套回复）

### 4. 安全模块
- 登录验证拦截器
- 会话管理

## 架构模式

### IoC（控制反转）
- 使用Spring Boot内置IoC容器管理Bean生命周期
- 通过依赖注入实现松耦合
- 自动扫描和注册组件

### MVC（模型-视图-控制器）
- **模型层**: 实体类和业务逻辑接口
- **视图层**: Thymeleaf模板页面
- **控制器层**: 处理HTTP请求和响应

## 快速开始

### 环境要求
- Java 17+
- Maven 3.6+
- MySQL数据库

### 本地运行

1. 克隆项目
```bash
git clone <repository-url>
```

2. 配置数据库连接（在[application.properties](file:///D%3A/A-development-project/IDEA-Java/SpringDemo01/src/main/resources/application.properties)中）
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/spring_demo
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. 编译并运行
```bash
mvn clean package
mvn spring-boot:run
```

### WAR包部署

1. 构建WAR包
```bash
mvn clean package
```

2. 将生成的WAR包部署到外部Tomcat服务器

## API接口

### 用户相关
- `GET /login` - 显示登录页面
- `POST /login` - 用户登录
- `GET /register` - 显示注册页面
- `POST /register` - 用户注册
- `GET /logout` - 用户登出

### 文章相关
- `GET /` 或 `GET /posts` - 文章列表
- `GET /posts/new` - 显示创建文章页面
- `POST /posts` - 创建新文章
- `GET /posts/view/{id}` - 查看文章详情

### 评论相关
- `POST /posts/comment/{postId}` - 添加评论
- `POST /posts/comment/{postId}/reply/{commentId}` - 回复评论

## 配置说明

### application.properties
```properties
# 应用名称
spring.application.name=SpringDemo01

# Thymeleaf配置
spring.thymeleaf.enabled=true
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.mode=HTML
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.cache=false
```

## 安全特性

- 登录验证拦截器保护需要认证的页面
- 验证码防止自动化攻击
- 会话管理确保用户状态安全

## 扩展建议

- 添加数据持久化（使用JPA/Hibernate）
- 实现角色权限管理
- 添加文件上传功能
- 集成缓存机制
- 添加API文档（Swagger）
- 实现搜索功能

