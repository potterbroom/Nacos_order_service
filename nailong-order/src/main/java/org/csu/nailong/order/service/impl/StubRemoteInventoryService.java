package org.csu.nailong.order.service.impl;

import org.csu.nailong.order.entity.Item;
import org.csu.nailong.order.service.RemoteInventoryService;
import org.springframework.stereotype.Component;

@Component
public class StubRemoteInventoryService implements RemoteInventoryService {

    @Override
    public int getItemCount(int itemId) {
        // TODO: 后续通过远程调用商品/库存微服务获取真实库存
        return 0;
    }

    @Override
    public void updateItem(Item item) {
        // TODO: 后续通过远程调用商品/库存微服务更新库存信息
        throw new UnsupportedOperationException("Remote inventory update not implemented yet");
    }

    @Override
    public int getSupplierByItemId(int itemId) {
        // TODO: 后续通过远程调用商家微服务获取供应商信息
        return 0;
    }
}
