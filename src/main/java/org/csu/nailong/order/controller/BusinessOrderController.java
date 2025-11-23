package org.csu.nailong.order.controller;

import org.csu.nailong.order.common.CommonResponse;
import org.csu.nailong.order.entity.AfterSale;
import org.csu.nailong.order.entity.Order;
import org.csu.nailong.order.entity.OrderItem;
import org.csu.nailong.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/business/orders")
public class BusinessOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public CommonResponse<List<OrderItem>> myOrder(@RequestParam("supplierId") int supplierId) {
        List<Order> orderList = new ArrayList<>();
        List<OrderItem> orderItems = orderService.getOrderItems(supplierId, orderList, 1);
        // TODO: 原项目通过模板渲染页面，微服务化后仅提供 JSON 数据，由网关 + 前端项目负责页面展示
        return CommonResponse.createForSuccess(orderItems);
    }

    @GetMapping("/refresh")
    public CommonResponse<List<OrderItem>> updateMyOrder(@RequestParam("supplierId") int supplierId) {
        List<Order> orderList = new ArrayList<>();
        List<OrderItem> orderItems = orderService.getOrderItems(supplierId, orderList, 1);
        return CommonResponse.createForSuccess(orderItems);
    }

    @PostMapping("/status")
    public CommonResponse<Void> statusChange(@RequestBody Map<String, String> payload) {
        String orderId = payload.get("orderId");
        String nextStatus = payload.get("nextStatus");
        Order order = orderService.getOrderByOrderId(orderId);
        orderService.updateOrder(order, nextStatus);
        return CommonResponse.createForSuccess();
    }

    @PostMapping("/after-sale")
    public CommonResponse<Void> updateAfterSale(@RequestBody Map<String, Object> payload) {
        String orderId = (String) payload.get("orderId");
        String operator = (String) payload.get("operator");
        Object flagObj = payload.get("flag");
        int flag = flagObj == null ? 0 : Integer.parseInt(flagObj.toString());
        AfterSale afterSale = orderService.getAfterSale(orderId);
        if ("supplier".equalsIgnoreCase(operator)) {
            afterSale.setBusiness_solve(flag);
            afterSale.setBusiness_solve_time(new Date());
        }
        if ("admin".equalsIgnoreCase(operator)) {
            afterSale.setAdmin_solve(flag);
            afterSale.setAdmin_solve_time(new Date());
        }
        orderService.updateAfterSale(afterSale);
        return CommonResponse.createForSuccess();
    }
}
