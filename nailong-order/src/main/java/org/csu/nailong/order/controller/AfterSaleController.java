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

@RestController
@RequestMapping("/api/after-sales")
public class AfterSaleController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{orderId}/actions")
    public CommonResponse<Void> handleButtonClick(@RequestBody Map<String, String> request,
                                                  @PathVariable String orderId) {
        int id = Integer.parseInt(request.get("id"));
        AfterSale afterSale = orderService.getAfterSale(orderId);
        afterSale.setAfter_sale_status(id);
        orderService.updateAfterSale(afterSale);
        return CommonResponse.createForSuccess();
    }

    @GetMapping
    public CommonResponse<AfterSale> statusChange(@RequestParam("orderId") String orderId) {
        AfterSale afterSale = orderService.getAfterSale(orderId);
        return CommonResponse.createForSuccess(afterSale);
    }
}
