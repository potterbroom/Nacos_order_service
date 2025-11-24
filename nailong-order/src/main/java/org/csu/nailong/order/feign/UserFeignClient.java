package org.csu.nailong.order.feign;

import org.csu.nailong.order.common.CommonResponse;
import org.csu.nailong.order.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", path = "")
public interface UserFeignClient {

    // TODO: 用户服务接口-根据 userId 获取用户详情；补充具体 HTTP 映射
    @GetMapping("api/users/{userId}")
    CommonResponse<User> getUser(@PathVariable int userId);

    // TODO: 用户服务接口-根据用户名查询 userId；补充具体 HTTP 映射
    @GetMapping("api/users")
    CommonResponse<User> getUserIdByUsername(@RequestParam String username);
}
