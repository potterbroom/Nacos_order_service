package org.csu.nailong.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {
    private int id;
    private String name;
    private int product_id;
    private String url;
    private int price;
    private String description;
    private int businessId;
    private boolean isListing;
    private boolean isDelete;
    private int remainingNumb;
}
