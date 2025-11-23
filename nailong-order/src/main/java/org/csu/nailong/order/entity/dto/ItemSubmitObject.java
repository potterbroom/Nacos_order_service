package org.csu.nailong.order.entity.dto;

import lombok.Data;
import org.csu.nailong.order.entity.Item;

@Data
public class ItemSubmitObject {
    private int userId;
    private String addressID;
    private Item item;
}
