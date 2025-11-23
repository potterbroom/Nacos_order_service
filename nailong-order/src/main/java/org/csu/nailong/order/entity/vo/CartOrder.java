package org.csu.nailong.order.entity.vo;

import lombok.Data;
import org.csu.nailong.order.entity.Address;
import org.csu.nailong.order.entity.CartItem;

import java.util.List;

@Data
public class CartOrder {

    private List<CartItem> cartItemList;

    private List<Address> addressList;
}
