package org.csu.nailong.order.controller;

import org.csu.nailong.order.common.CommonResponse;
import org.csu.nailong.order.entity.OrderItem;
import org.csu.nailong.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    // Controller -> OrderService -> RemoteInventoryService（封装商品/库存调用）
    @GetMapping
    public CommonResponse<List<OrderItem>> getAllOrders() {
        List<OrderItem> orderItems = orderService.getAllOrders();
        //TODO：原来的项目是通过渲染 HTML 模板来返回页面；微服务版本应该只返回 JSON 数据，页面展示交给网关和前端来处理。
        return CommonResponse.createForSuccess(orderItems);
    }

    // Controller -> OrderService -> RemoteUserService（封装用户查询）+ RemoteInventoryService（封装商品信息）
    @GetMapping("/search")
    public CommonResponse<List<OrderItem>> searchUserOrder(@RequestParam("username") String username) {
        List<OrderItem> orderItems = orderService.getOrderItemsByClient(username, 0);
        return CommonResponse.createForSuccess(orderItems);
    }

    // Controller -> OrderService -> RemoteInventoryService（检查超时需要商品信息）
    @GetMapping("/timeout")
    public CommonResponse<List<OrderItem>> getTimeoutOrders() {
        List<OrderItem> timeoutOrderItems = orderService.getTimeoutOrderItems();
        return CommonResponse.createForSuccess(timeoutOrderItems);
    }
}
