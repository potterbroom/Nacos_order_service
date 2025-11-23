package org.csu.nailong.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("address")
public class Address {

    @TableId(type = IdType.AUTO)
    private int id;
    private int userId;

    private String province;
    private String city;
    private String district;
    private String detailAddress;
    private String phoneNumber;
    private String receiverName;
    private Integer isDefault;
}
