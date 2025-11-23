package org.csu.nailong.order.feign;

import org.csu.nailong.order.entity.User;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-service", path = "")
public interface UserFeignClient {

    // TODO: 用户服务接口-根据 userId 获取用户详情；补充具体 HTTP 映射
    User getUser(int userId);

    // TODO: 用户服务接口-根据用户名查询 userId；补充具体 HTTP 映射
    Integer getUserIdByUsername(String username);
}
