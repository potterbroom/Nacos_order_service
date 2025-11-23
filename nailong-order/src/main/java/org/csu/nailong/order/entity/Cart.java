package org.csu.nailong.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    private List<CartItem> itemList = new ArrayList<>();
    private int userId;
    private int totalPrice;
    private int totalCount;
}
