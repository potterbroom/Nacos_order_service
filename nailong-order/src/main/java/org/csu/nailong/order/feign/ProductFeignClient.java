package org.csu.nailong.order.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "product-service", path = "")
public interface ProductFeignClient {
}
