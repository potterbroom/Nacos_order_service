package org.csu.nailong.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@TableName("orders")
public class Order {
    @TableId(value = "order_id", type = IdType.INPUT)
    private String order_id;
    private int client;
    private int address_id;
    private int item_id;
    private int amount;
    private int total_price;
    private int supplier;
    private int status;
    private Date create_time;
    private Date pay_time;
    private String pay_method;
    private Date ship_time;
    private Date confirm_time;
    private Date after_sale_time;
    private String remark;
    private int is_occupy;
}
