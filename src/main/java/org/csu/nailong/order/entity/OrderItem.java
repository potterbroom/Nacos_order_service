package org.csu.nailong.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {
    private String order_id;
    private int itemID;
    private int itemNum;
    private String name;
    private String url;
    private int price;
    private int userID;
    private int status;
}
