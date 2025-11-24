package org.csu.nailong.order.controller;

import org.csu.nailong.order.common.CommonResponse;
import org.csu.nailong.order.entity.AfterSale;
import org.csu.nailong.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// TODO: 当前售后 API 只处理订单表和售后表数据，暂未涉及用户或商品/库存模块调用
@RestController
@RequestMapping("/api/after-sales")
public class AfterSaleController {

    @Autowired
    private OrderService orderService;

    // Controller -> OrderService -> AfterSale persistence (后续如需联动用户/库存，再从服务层调用 RemoteXXXService)
    @PostMapping("/{orderId}/actions")
    public CommonResponse<Void> handleButtonClick(@RequestBody Map<String, String> request,
                                                  @PathVariable String orderId) {
        int id = Integer.parseInt(request.get("id"));
        orderService.updateAfterSaleStatus(orderId, id);
        return CommonResponse.createForSuccess();
    }

    // Controller -> OrderService -> AfterSaleMapper（无跨模块依赖）
    @GetMapping
    public CommonResponse<AfterSale> statusChange(@RequestParam("orderId") String orderId) {
        AfterSale afterSale = orderService.getAfterSale(orderId);
        return CommonResponse.createForSuccess(afterSale);
    }
}
