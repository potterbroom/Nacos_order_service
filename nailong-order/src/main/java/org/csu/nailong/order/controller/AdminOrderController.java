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

    @GetMapping
    public CommonResponse<List<OrderItem>> getAllOrders() {
        List<OrderItem> orderItems = orderService.getAllOrders();
        // TODO: 原项目通过模板渲染页面，微服务化后仅提供 JSON 数据，由网关 + 前端项目负责页面展示
        return CommonResponse.createForSuccess(orderItems);
    }

    @GetMapping("/search")
    public CommonResponse<List<OrderItem>> searchUserOrder(@RequestParam("username") String username) {
        List<OrderItem> orderItems = orderService.getOrderItemsByClient(username, 0);
        return CommonResponse.createForSuccess(orderItems);
    }

    @GetMapping("/timeout")
    public CommonResponse<List<OrderItem>> getTimeoutOrders() {
        List<OrderItem> timeoutOrderItems = orderService.getTimeoutOrderItems();
        return CommonResponse.createForSuccess(timeoutOrderItems);
    }
}
