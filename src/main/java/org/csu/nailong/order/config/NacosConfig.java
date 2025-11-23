package org.csu.nailong.order.config;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NacosConfig {

    @Bean
    @RefreshScope
    @ConfigurationProperties(prefix = "spring.cloud.nacos.discovery")
    public NacosDiscoveryProperties nacosDiscoveryProperties() {
        return new NacosDiscoveryProperties();
    }

    @Bean
    @RefreshScope
    @ConfigurationProperties(prefix = "spring.cloud.nacos.config")
    public NacosConfigProperties nacosConfigProperties() {
        return new NacosConfigProperties();
    }
}
