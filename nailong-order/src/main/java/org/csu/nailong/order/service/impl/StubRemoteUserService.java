package org.csu.nailong.order.service.impl;

import org.csu.nailong.order.entity.User;
import org.csu.nailong.order.service.RemoteUserService;
import org.springframework.stereotype.Component;

@Component
public class StubRemoteUserService implements RemoteUserService {

    @Override
    public User getUser(int userId) {
        // TODO: 后续通过远程调用用户微服务获取真实的用户信息
        return null;
    }
}
