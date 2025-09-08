ZZULI-Board-Backend
===

📖 项目简介
---

ZZULI-Board-Backend 是一个基于Spring Boot框架开发的编程竞赛管理后端系统。该系统专为教育机构设计，提供完整的编程竞赛组织、管理和监控功能，支持与PTA（Programming Training Assistant）平台的深度集成。

✨ 主要功能
---

### 🏆 竞赛管理

- **竞赛创建与配置**：支持CCPC/ICPC和GPLT两种竞赛模式
- **实时排行榜**：提供冻结排行榜功能和气球配送追踪
- **成绩统计**：自动计算分数和排名，支持不同评分策略 

### 👥 用户注册管理

- **个人注册**：支持单人参赛模式
- **团队注册**：支持多人组队参赛
- **机构管理**：层次化管理学校、学院、班级结构 

### 🔗 外部平台集成

- **PTA平台对接**：自动同步题目列表和提交记录
- **实时数据获取**：定时轮询PTA平台获取最新提交状态
- **异步处理**：后台任务处理大数据量同步

### 🛡️ 安全认证

- **JWT身份验证**：管理员操作需要JWT令牌认证
- **权限控制**：细粒度的接口访问控制 

🛠️ 技术栈
---

### 后端框架

- **Spring Boot 2.7.3** - 主应用框架
- **MyBatis** - 数据持久化框架
- **Spring Security** - 安全认证 pom.xml:13-18

### 数据存储

- **MySQL** - 主数据库，存储竞赛、用户、成绩等数据
- **Redis** - 缓存层，提供高性能数据访问和任务调度
- **Druid** - 数据库连接池 

### 开发工具

- **Maven** - 项目构建管理
- **Lombok** - 简化Java代码编写
- **Jackson** - JSON序列化/反序列化
- **Log4j2** - 日志管理 

📁 项目结构
---

```
src/main/java/zzuli/  
├── common/          # 通用组件和工具类  
├── config/          # 配置类  
├── controller/      # 控制器层  
│   ├── admin/       # 管理员接口  
│   └── user/        # 用户接口  
├── handler/         # 异常处理器  
├── interceptor/     # 拦截器  
├── mapper/          # MyBatis映射器  
├── pojo/           # 数据传输对象  
│   ├── dto/        # 数据传输对象  
│   ├── entity/     # 实体类  
│   ├── pta/        # PTA平台相关对象  
│   └── vo/         # 视图对象  
├── service/        # 业务逻辑层  
├── task/           # 定时任务  
└── utils/          # 工具类  
```

zzuli:1

🚀 快速开始
---

### 环境要求

- Java 16+
- MySQL 8.0+
- Redis 6.0+
- Maven 3.6+

### 安装步骤

1. **克隆项目**

```
git clone https://github.com/ysehertz/ZZULI-Board-Backend.git  
cd ZZULI-Board-Backend
```

1. **配置数据库**

   根据`application.yml`配置文件，设置以下环境变量：

   - `board.datasource.host` - MySQL主机地址
   - `board.datasource.port` - MySQL端口
   - `board.datasource.database` - 数据库名称
   - `board.datasource.username` - 数据库用户名
   - `board.datasource.password` - 数据库密码 application.yml:14-17

2. **配置Redis**

   设置Redis连接参数：

   - `board.redis.host` - Redis主机地址
   - `board.redis.port` - Redis端口
   - `board.redis.database` - Redis数据库编号
   - `board.redis.password` - Redis密码 application.yml:18-22

3. **编译运行**

```
mvn clean package  
java -jar target/ZZULI-Board-Backend-1.0-SNAPSHOT.jar
```

📚 API文档
---

### Normal User API

| 接口名称     | 请求路径                                  | 方法 | 请求头 | 请求参数 | 备注                   |
| ------------ | ----------------------------------------- | ---- | ------ | -------- | ---------------------- |
| 获取比赛列表 | `/api/contest/list`                       | GET  | 无     | 无       | 测试通过               |
| 获取比赛信息 | `/api/contest/config?contest_id=xxxxxxx`  | GET  | 无     | 比赛ID   | 测试通过               |
| 获取考场列表 | `/api/room/list?contest_id=xxxxxxx`       | GET  | 无     | 比赛ID   | 测试通过               |
| 获取考生列表 | `/api/member/list?contest_id=xxxxxxx`     | GET  | 无     | 比赛ID   | -                      |
| 获取队伍列表 | `/api/team/list?contest_id=xxxxxxx`       | GET  | 无     | 比赛ID   | -                      |
| 获取评测记录 | `/api/record?contest_id=xxxxxxx`          | GET  | 无     | 比赛ID   | 测试通过               |
| 获取学校列表 | `/api/school`                             | GET  | 无     | 无       | 测试通过               |
| 获取学院列表 | `/api/college?school_id=xxx`              | GET  | 无     | 学校ID   | 测试通过               |
| 获取班级列表 | `/api/class?college_id=xxx`               | GET  | 无     | 学院ID   | 测试通过               |
| 团体报名     | `/api/register/team?contest_id=xxxxxxx`   | POST | 无     | 比赛ID   | -                      |
| 个人报名     | `/api/register/single?contest_id=xxxxxxx` | POST | 无     | 比赛ID   | 功能已实现，未测试通过 |

------

### Admin User API

| 接口名称   | 请求路径                                                     | 方法 | 请求头       | 请求参数     | 备注                         |
| ---------- | ------------------------------------------------------------ | ---- | ------------ | ------------ | ---------------------------- |
| 管理员登录 | `/api/login`                                                 | POST | 无           | 无           | md5加密，返回token，测试通过 |
| 创建比赛   | `/api/admin/contest/create`                                  | POST | BoardSession | 无           | 测试通过                     |
| 获取比赛   | `/api/admin/contest/config?contest_id=xxxxxxx`               | GET  | BoardSession | 比赛ID       | 未测试                       |
| 删除比赛   | `/api/admin/contest/delete?contest_id=xxxxxxx`               | POST | BoardSession | 比赛ID       | -                            |
| 修改比赛   | `/api/admin/contest/set?contest_id=xxxxxxx`                  | POST | BoardSession | 比赛ID       | 测试通过                     |
| 创建学校   | `/api/admin/school/create`                                   | POST | BoardSession | 无           | 测试通过                     |
| 修改学校   | `/api/admin/school/set?school_id=xxxxxxx`                    | POST | BoardSession | 学校ID       | 测试通过                     |
| 删除学校   | `/api/admin/school/delete?school_id=xxxxxxx`                 | POST | BoardSession | 学校ID       | 测试通过                     |
| 创建学院   | `/api/admin/college/create?school_id=xxxx`                   | POST | BoardSession | 学校ID       | 测试通过                     |
| 修改学院   | `/api/admin/college/set?college_id=xxxxxxx`                  | POST | BoardSession | 学院ID       | 测试通过                     |
| 删除学院   | `/api/admin/college/delete?college_id=xxxxxxx`               | POST | BoardSession | 学院ID       | 测试通过                     |
| 创建班级   | `/api/admin/class/create?college_id=xxx`                     | POST | BoardSession | 学院ID       | 测试通过                     |
| 修改班级   | `/api/admin/class/set?class_id=xxxxxxx`                      | POST | BoardSession | 班级ID       | 测试通过                     |
| 删除班级   | `/api/admin/class/delete?class_id=xxxxxxx`                   | POST | BoardSession | 班级ID       | 测试通过                     |
| 创建考场   | `/api/admin/room/create?contest_id=xxxxxxx`                  | POST | BoardSession | 比赛ID       | 测试通过                     |
| 修改考场   | `/api/admin/room/set?room_id=xxxxxxx`                        | POST | BoardSession | 房间ID       | 测试通过                     |
| 删除考场   | `/api/admin/room/delete?room_id=xxxxxxx`                     | POST | BoardSession | 房间ID       | 测试通过                     |
| 修改队伍   | `/api/admin/team/set?team_id=xxxxxxx`                        | POST | BoardSession | 队伍ID       | -                            |
| 删除队伍   | `/api/admin/team/delete?team_id=xxxxxxx`                     | POST | BoardSession | 队伍ID       | -                            |
| 修改考生   | `/api/admin/member/set?contest_id=xxxxxxx&member_id=xxxxxxx` | POST | BoardSession | 比赛ID、学号 | -                            |
| 删除考生   | `/api/admin/member/delete?contest_id=xxxxxxx&member_id=xxxxxxx` | POST | BoardSession | 比赛ID、学号 | -                            |

⚙️ 配置说明
---

### JWT配置

系统使用JWT进行身份验证，相关配置如下：

- `zzuli.jwt.admin-secret-key` - JWT签名密钥
- `zzuli.jwt.admin-ttl` - Token过期时间（毫秒）
- `zzuli.jwt.admin-token-name` - Token名称 

### 任务调度配置

- `task.frequency.getRecord` - 获取记录任务频率（秒）
- `task.frequency.start` - 启动检查任务频率（秒） application.yml:51-54

🏗️ 架构设计
---

系统采用分层架构设计，包含以下核心组件：

- **表现层**：RESTful API接口，支持JSON数据交换
- **业务逻辑层**：核心业务处理，包括竞赛管理和PTA集成
- **数据访问层**：MyBatis ORM映射，支持MySQL数据持久化
- **缓存层**：Redis缓存，提升系统性能
- **任务调度层**：定时任务处理，实现数据同步 ContestServiceImpl.java:359-381

🔄 后台任务
---

系统包含两个核心后台任务：

1. **StartTask** - 监控竞赛开始状态，自动触发记录获取
2. **GetRecordTask** - 定期从PTA平台获取提交记录并同步到本地 GetRecordTask.java:37-54

👥 贡献
---

欢迎提交Issue和Pull Request来帮助改进项目！

📞 联系方式
---

如有问题或建议，请通过GitHub Issues联系我们。