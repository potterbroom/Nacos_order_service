package org.csu.nailong.order.feign;

import org.csu.nailong.order.entity.Item;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service", path = "")
public interface ProductFeignClient {

    // TODO: 商品/库存服务接口-查询库存数量；补充 HTTP mapping
//    int getItemCount(int itemId);

    // TODO: 商品/库存服务接口-获取商品详情；补充 HTTP mapping
    @GetMapping("/api/products/{itemId}")
    Item getItemById(@PathVariable int itemId);

    // TODO: 商品/库存服务接口-更新库存；补充 HTTP mapping
    @PutMapping("/api/products/{itemId}/")
    Item updateItem(@PathVariable int itemId, @RequestBody Item item);

    // TODO: 商品/库存服务接口-根据 SKU 查询供应商；补充 HTTP mapping
//    int getSupplierByItemId(int itemId);
}
