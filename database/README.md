# 数据库结构说明

订单微服务只维护订单和售后相关的核心表，用户、商品、库存等信息会通过远程微服务（例如 RemoteUserService、RemoteInventoryService）提供的数据来补足，因此本目录只包含生成订单所需的结构。

## 表结构概览

### address
- **主键**：`id`（自增）。
- **关键字段**：`user_id`（地址归属用户）、`detail_address`、`receiver_name`、`phone_number` 等。
- **用途**：存放收货地址，`orders.address_id` 外键指向该表，用于支付/发货时读取地址信息。

### orders
- **主键**：`order_id`（字符串，时间戳 + 随机数）。
- **关键字段**：`client`（买家）、`item_id`、`amount`、`total_price`、`status`、`is_occupy`、以及若干时间戳字段（支付、发货、确认收货、售后申请）。
- **用途**：记录订单总体信息，对接地址、商品、供应商。`order_service` 的增删改查均围绕此表。

### order_item
- **主键**：`id`（自增）。
- **关键字段**：`order_id`（关联订单）、`item_id`、`item_num`、`item_price`、`user_id`、`status`。
- **用途**：保存订单里每个商品条目，方便“我的订单”或“商家订单”接口直接返回展示所需的商品明细。

### after_sale
- **主键**：`order_id`（同时是外键）。
- **关键字段**：`after_sale_status`、`business_solve`、`admin_solve` 以及对应的处理时间。
- **用途**：追踪售后流程，供 `AfterSaleController` 与订单状态流转功能使用。

## 与其它微服务的关系
该数据库只包含订单域的核心表，用户、商品、库存数据通过远程调用获取，不在当前 schema 中维护，从而保持微服务职责单一。

## 初始化
执行 `mysql -u root -p < database/schema.sql` 即可创建数据表并写入两条示例地址、两笔示例订单以及对应的订单项/售后记录，方便在开发环境快速验证接口。
