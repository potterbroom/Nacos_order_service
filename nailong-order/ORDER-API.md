### API接口汇总表

| HTTP方法                    | 路径                                 | 功能描述                     | 请求体                          | 响应体            |
| :-------------------------- | :----------------------------------- | :--------------------------- | :------------------------------ | :---------------- |
| **AdminOrderController**    |                                      |                              |                                 |                   |
| GET                         | `/api/admin/orders`                  | 获取所有订单                 | 无                              | `List<OrderItem>` |
| GET                         | `/api/admin/orders/search`           | 按用户名搜索用户订单         | 无（查询参数：username）        | `List<OrderItem>` |
| GET                         | `/api/admin/orders/timeout`          | 获取超时订单                 | 无                              | `List<OrderItem>` |
| **AfterSaleController**     |                                      |                              |                                 |                   |
| POST                        | `/api/after-sales/{orderId}/actions` | 处理售后按钮点击操作         | `Map<String, String>`           | `Void`            |
| GET                         | `/api/after-sales`                   | 获取售后状态                 | 无（查询参数：orderId）         | `AfterSale`       |
| **BusinessOrderController** |                                      |                              |                                 |                   |
| GET                         | `/api/business/orders`               | 获取供应商订单               | 无（查询参数：supplierId）      | `List<OrderItem>` |
| GET                         | `/api/business/orders/refresh`       | 刷新供应商订单               | 无（查询参数：supplierId）      | `List<OrderItem>` |
| POST                        | `/api/business/orders/status`        | 更改订单状态                 | `Map<String, String>`           | `Void`            |
| POST                        | `/api/business/orders/after-sale`    | 更新售后信息                 | `Map<String, Object>`           | `Void`            |
| **OrderController**         |                                      |                              |                                 |                   |
| GET                         | `/api/orders/orderForm`              | 获取用户地址列表（订单表单） | 无（查询参数：userId）          | `List<Address>`   |
| GET                         | `/api/orders/singleOrder`            | 获取用户地址列表（单订单）   | 无（查询参数：userId）          | `List<Address>`   |
| GET                         | `/api/orders/cartOrder`              | 获取购物车订单信息           | 无（查询参数：userId）          | `CartOrder`       |
| POST                        | `/api/orders/CartSubmit`             | 提交购物车订单               | 无（查询参数：userId, address） | `List<Order>`     |
| POST                        | `/api/orders/ItemSubmit`             | 提交单个商品订单             | `ItemSubmitObject`              | `Order`           |
| POST                        | `/api/orders/addresses`              | 添加新地址                   | `Address`                       | `Void`            |
| DELETE                      | `/api/orders/addresses/{addressId}`  | 删除地址                     | 无                              | `Void`            |
| GET                         | `/api/orders/orders/{userId}`        | 获取用户订单列表             | 无                              | `List<OrderItem>` |
| PUT                         | `/api/orders/orderStatus`            | 批量更改订单状态             | `OrderStatusChangeRequest1`     | `Void`            |
| PUT                         | `/api/orders/status`                 | 更改单个订单状态             | `OrderStatusChangeRequest2`     | `Void`            |
| GET                         | `/api/orders/newOrders/{userId}`     | 刷新用户订单列表             | 无                              | `List<OrderItem>` |
| GET                         | `/api/orders/address/{orderId}`      | 根据订单ID获取地址           | 无                              | `Address`         |
| GET                         | `/api/orders/{orderId}`              | 根据订单ID获取订单           | 无                              | `Order`           |