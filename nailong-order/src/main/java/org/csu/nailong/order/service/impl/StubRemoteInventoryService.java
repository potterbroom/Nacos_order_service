package org.csu.nailong.order.service.impl;

import org.csu.nailong.order.dao.ItemDao;
import org.csu.nailong.order.entity.Item;
import org.csu.nailong.order.feign.ProductFeignClient;
import org.csu.nailong.order.service.RemoteInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StubRemoteInventoryService implements RemoteInventoryService {

    private final ItemDao itemDao;

    @Autowired
    public StubRemoteInventoryService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public int getItemCount(int itemId) {
        // 商品/库存模块能力：未来通过远程调用查询库存，当前使用本地 DAO 模拟
        Item item = productFeignClient.getItemById(itemId);
        return item == null ? 0 : item.getRemainingNumb();
    }

    @Override
    public Item getItemById(int itemId) {
        // 商品/库存模块能力：获取商品详情（后续替换为远程调用）
//        return itemDao.getItem(itemId);
        return productFeignClient.getItemById(itemId);
    }

    @Override
    public void updateItem(Item item) {
        // 商品/库存模块能力：更新库存数据，暂未接入远程微服务因此先空实现
        productFeignClient.updateItem(item.getId(), item);
    }

    @Override
    public int getSupplierByItemId(int itemId) {
        // 商品/库存模块能力：根据商品查询供应商 ID，目前用本地查询模拟
        Item item = productFeignClient.getItemById(itemId);
        return item == null ? 0 : item.getBusinessId();
    }
}
