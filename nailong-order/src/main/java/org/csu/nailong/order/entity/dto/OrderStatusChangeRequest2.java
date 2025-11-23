package org.csu.nailong.order.entity.dto;

import lombok.Data;

@Data
public class OrderStatusChangeRequest2 {

    private String orderId;
    private String nextStatus;
}
