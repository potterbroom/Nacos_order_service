package org.csu.nailong.order.service;

import org.csu.nailong.order.entity.Item;

public interface RemoteInventoryService {

    int getItemCount(int itemId);

    void updateItem(Item item);

    int getSupplierByItemId(int itemId);
}
