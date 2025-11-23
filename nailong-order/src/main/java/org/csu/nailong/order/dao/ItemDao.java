package org.csu.nailong.order.dao;

import org.apache.ibatis.annotations.Mapper;
import org.csu.nailong.order.entity.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ItemDao {

    List<Item> getItemListByProduct(int product_Id);
    List<Item> getItemListByProductAndIgnoreListing(int product_Id);

    Item getItem(int itemId);

    int insertItem(Item item);

    int deleteItem(int itemId);

    int getItemId(String itemName);

    List<Item> SearchItems(String keyword);
}
