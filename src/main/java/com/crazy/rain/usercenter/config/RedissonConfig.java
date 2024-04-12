package com.crazy.rain.usercenter.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: RedissonConfig
 * @Description: 分布式锁配置
 * @author: CrazyRain
 * @date: 2024/4/4 17:37
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Slf4j
@Data
public class RedissonConfig {

    private String host;
    private String port;
    private String password;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        String address = String.format("redis://%s:%s", host, port);
        config.useSingleServer().setAddress(address).setDatabase(3).setPassword(password);
        return Redisson.create(config);
    }
}
