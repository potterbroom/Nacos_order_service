package org.csu.nailong.order.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderStatusChangeRequest1 {
    private int userId;
    private String behavior;
    private String nextStatus;
    private List<String> currentOrderList;
}
