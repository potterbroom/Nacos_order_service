package org.csu.nailong.order.dao;

import org.apache.ibatis.annotations.Mapper;
import org.csu.nailong.order.entity.CartItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CartDao {
    void executeAddCart(int userID, int itemID, int itemNum);
    void executeRemoveCart(int userID, int itemID);
    List<CartItem> searchUserCartItems(int userID);
    void coverCartItem(int userID, int itemID);
    int getCartCount(int userID, int itemID);
}
