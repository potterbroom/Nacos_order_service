package org.csu.nailong.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.nailong.order.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper extends BaseMapper<Order> {
}
