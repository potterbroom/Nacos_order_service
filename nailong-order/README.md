# nailong-order

nailong-order 是从原 NaiLongOSS 单体应用中抽取出的 **订单+售后微服务**，只提供 REST/JSON API，负责订单创建、查询、订单项管理以及售后处理，页面渲染由网关/前端工程完成。

## 技术栈
- Spring Boot 3.2.0
- Spring Cloud Alibaba 2023.0.1.0（Nacos discovery/config）
- MyBatis Plus 3.5.10.1
- MySQL 8.x
- Lombok
- Spring Validation / Spring Web / Actuator

## 快速启动
1. **初始化数据库**：创建数据库并执行 `database/schema.sql` 导入表结构及示例数据。
2. **配置环境**：在 `src/main/resources/application.yml` 或环境变量中设置 MySQL 连接、Nacos 地址等参数。
3. **构建项目**：执行 `mvn clean package`。
4. **启动服务**：运行 `java -jar target/nailong-order-0.0.1-SNAPSHOT.jar`（可配合 `--spring.profiles.active`/环境变量覆盖配置）。

## 微服务边界
- 本服务只维护订单核心表（address / orders / order_item / after_sale）。
- 用户信息、商品/库存、购物车等能力未来由独立微服务提供；当前代码中已以 `RemoteUserService`、`RemoteInventoryService` 等接口及 TODO 注释预留对接点。
- 通过 `CommonResponse` 统一响应结构和 `ResponseCode` 错误码，与上游/下游服务通信时保持一致的 JSON 契约。

更多接口规范见 [API_DESIGN_GUIDE.md](API_DESIGN_GUIDE.md)。
