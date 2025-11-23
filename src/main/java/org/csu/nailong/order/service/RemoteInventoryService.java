package org.csu.nailong.order.service;

import org.csu.nailong.order.entity.Item;

public interface RemoteInventoryService {

    /**
     * 商品/库存模块：查询指定 SKU 的库存数量
     */
    int getItemCount(int itemId);

    /**
     * 商品/库存模块：根据 itemId 获取商品及库存等详细信息
     */
    Item getItemById(int itemId);

    /**
     * 商品/库存模块：更新库存或其相关属性（未来通过远程调用实现）
     */
    void updateItem(Item item);

    /**
     * 商品/库存模块：根据商品确定供应商 ID
     */
    int getSupplierByItemId(int itemId);
}
