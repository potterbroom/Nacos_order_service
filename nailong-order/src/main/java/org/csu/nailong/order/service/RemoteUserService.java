package org.csu.nailong.order.service;

import org.csu.nailong.order.entity.User;

public interface RemoteUserService {

    /**
     * 用户模块：根据用户 ID 获取用户详情（未来通过用户微服务的 Feign 调用）
     */
    User getUser(int userId);

    /**
     * 用户模块：根据用户名解析用户 ID（后续将交由 RemoteUserService 调用用户微服务）
     */
    Integer getUserIdByUsername(String username);
}
