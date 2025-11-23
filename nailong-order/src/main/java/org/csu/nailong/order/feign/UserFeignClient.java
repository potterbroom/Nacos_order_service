package org.csu.nailong.order.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-service", path = "")
public interface UserFeignClient {
}
