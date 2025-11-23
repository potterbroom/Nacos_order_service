package org.csu.nailong.order.feign;

import org.csu.nailong.order.entity.Item;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "product-service", path = "")
public interface ProductFeignClient {

    // TODO: 商品/库存服务接口-查询库存数量；补充 HTTP mapping
    int getItemCount(int itemId);

    // TODO: 商品/库存服务接口-获取商品详情；补充 HTTP mapping
    Item getItemById(int itemId);

    // TODO: 商品/库存服务接口-更新库存；补充 HTTP mapping
    void updateItem(Item item);

    // TODO: 商品/库存服务接口-根据 SKU 查询供应商；补充 HTTP mapping
    int getSupplierByItemId(int itemId);
}
