# API 设计指南

## 统一响应
所有接口返回 `CommonResponse`，包含以下字段：
- `status`：整型，参考 `ResponseCode` 的 code。
- `message`：描述性文字，成功时通常为 `"Success"`，错误时提供提示。
- `data`：可选，实际业务数据，类型根据接口而定。

示例（成功）：
```json
{
  "status": 0,
  "message": "Success",
  "data": {
    "orderId": "202401010001",
    "status": 1
  }
}
```

## 错误码约定
部分常用 `ResponseCode`：
- `0 SUCCESS`：请求成功。
- `1 ERROR`：通用失败。
- `10 ILLEGAL_ARGUMENT`：参数非法。
- `400 BAD_REQUEST`：业务校验失败。
- `401 UNAUTHORIZED`、`403 FORBIDDEN`：鉴权/权限问题。
- `404 NOT_FOUND`：资源不存在。
- `500 INTERNAL_SERVER_ERROR`：服务器内部错误。

## URL 命名规范
- 用户订单接口：`/api/orders/**`
- 后台订单接口：`/api/admin/orders/**`
- 商家订单接口：`/api/business/orders/**`
- 售后接口：`/api/after-sales/**`

所有路径使用 REST 风格与小写连字符或复数名词。

## 典型接口示例

### 1. 创建订单（单个商品）
- **Method**：`POST /api/orders/ItemSubmit`
- **Request Body**
```json
{
  "userId": 1,
  "addressID": "1",
  "item": {
    "id": 101,
    "name": "奶龙限定手办",
    "price": 1999,
    "product_id": 10
  }
}
```
- **Response**
```json
{
  "status": 0,
  "message": "Success",
  "data": {
    "order_id": "202401010001",
    "status": 0,
    "total_price": 1999
  }
}
```

### 2. 查询订单详情
- **Method**：`GET /api/orders/{orderId}`
- **Response**
```json
{
  "status": 0,
  "message": "Success",
  "data": {
    "order_id": "202401010001",
    "client": 1,
    "status": 1,
    "amount": 2,
    "total_price": 3998
  }
}
```

### 3. 更新订单状态（批量）
- **Method**：`PUT /api/orders/orderStatus`
- **Request Body**
```json
{
  "behavior": "payCartOrder",
  "nextStatus": "1",
  "currentOrderList": [
    "202401010001",
    "202401010002"
  ]
}
```
- **Response**
```json
{
  "status": 0,
  "message": "Success",
  "data": null
}
```

更多接口遵循同样的响应结构和 URL 规范，所有异常由 `GlobalException` 统一转换为 `CommonResponse`。
