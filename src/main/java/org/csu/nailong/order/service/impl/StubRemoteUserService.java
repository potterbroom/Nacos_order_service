package org.csu.nailong.order.service.impl;

import org.csu.nailong.order.dao.UserDao;
import org.csu.nailong.order.entity.User;
import org.csu.nailong.order.service.RemoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StubRemoteUserService implements RemoteUserService {

    private final UserDao userDao;

    @Autowired
    public StubRemoteUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUser(int userId) {
        // 用户模块能力：未来调用用户微服务；当前用本地 DAO 模拟远程结果
        return userDao.getUser(userId);
    }

    @Override
    public Integer getUserIdByUsername(String username) {
        // 用户模块能力：根据用户名获取 userId，后续会替换为远程服务
        return userDao.getUserIdByUsername(username);
    }
}
